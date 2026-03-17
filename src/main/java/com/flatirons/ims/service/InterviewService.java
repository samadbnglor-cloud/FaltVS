package com.flatirons.ims.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flatirons.ims.constants.AppConstants;
import com.flatirons.ims.dto.InterviewResponse;
import com.flatirons.ims.dto.ScheduleInterviewRequest;
import com.flatirons.ims.dto.UpdateInterviewRequest;
import com.flatirons.ims.entity.Candidate;
import com.flatirons.ims.entity.Interview;
import com.flatirons.ims.enums.InterviewStatus;
import com.flatirons.ims.entity.Interviewer;
import com.flatirons.ims.exception.ResourceNotFoundException;
import com.flatirons.ims.mapper.InterviewMapper;
import com.flatirons.ims.repository.CandidateRepository;
import com.flatirons.ims.repository.InterviewRepository;
import com.flatirons.ims.repository.InterviewSpecifications;
import com.flatirons.ims.repository.InterviewerRepository;

@Service
@Transactional
public class InterviewService {

    private static final Logger log = LoggerFactory.getLogger(InterviewService.class);

    private final InterviewRepository interviewRepository;
    private final CandidateRepository candidateRepository;
    private final InterviewerRepository interviewerRepository;
    private final InterviewMapper interviewMapper;
    private final NotificationPort notificationPort;

    public InterviewService(InterviewRepository interviewRepository,
                            CandidateRepository candidateRepository,
                            InterviewerRepository interviewerRepository,
                            InterviewMapper interviewMapper,
                            NotificationPort notificationPort) {
        this.interviewRepository = interviewRepository;
        this.candidateRepository = candidateRepository;
        this.interviewerRepository = interviewerRepository;
        this.interviewMapper = interviewMapper;
        this.notificationPort = notificationPort;
    }

    public InterviewResponse scheduleInterview(ScheduleInterviewRequest request) {
        if (request.getScheduledTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(AppConstants.CANNOT_SCHEDULE_PAST);
        }

        Candidate candidate = candidateRepository.findById(request.getCandidateId())
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.CANDIDATE_NOT_FOUND));
        Interviewer interviewer = interviewerRepository.findById(request.getInterviewerId())
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.INTERVIEWER_NOT_FOUND));

        validateNoConflict(interviewer.getId(), request.getScheduledTime(), null);

        Interview interview = new Interview(candidate, interviewer, request.getScheduledTime(), InterviewStatus.SCHEDULED);
        Interview saved = interviewRepository.save(interview);

        log.info("Interview {} scheduled for candidate {} with interviewer {}",
                saved.getId(), candidate.getName(), interviewer.getName());

       /* notificationPort.notifyInterviewScheduled(
            candidate.getEmail(), interviewer.getEmail(),
            candidate.getName(), interviewer.getName(),
            request.getScheduledTime().toString());*/

        return interviewMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<InterviewResponse> getInterviews(String candidateName, String interviewerName, Pageable pageable) {
        Specification<Interview> spec = (root, query, cb) -> cb.conjunction();
        if (candidateName != null && !candidateName.isEmpty()) {
            spec = spec.and(InterviewSpecifications.hasCandidateName(candidateName));
        }
        if (interviewerName != null && !interviewerName.isEmpty()) {
            spec = spec.and(InterviewSpecifications.hasInterviewerName(interviewerName));
        }
        return interviewRepository.findAll(spec, pageable)
                .map(interviewMapper::toResponse)
                .getContent();
    }

    public InterviewResponse updateInterview(UpdateInterviewRequest request) {
        Interview interview = interviewRepository.findById(request.getId())
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.INTERVIEW_NOT_FOUND));

        if (request.getStatus() == InterviewStatus.COMPLETED) {
            throw new IllegalArgumentException(AppConstants.COMPLETED_ONLY_VIA_FEEDBACK);
        }

        if (request.getStatus() == InterviewStatus.CANCELLED) {
            interview.cancel();
        } else if (request.getScheduledTime() != null) {
            if (request.getScheduledTime().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException(AppConstants.CANNOT_SCHEDULE_PAST);
            }
            validateNoConflict(interview.getInterviewer().getId(), request.getScheduledTime(), interview.getId());
            interview.reschedule(request.getScheduledTime());
        } else {
            interview.setStatus(request.getStatus());
        }

        Interview saved = interviewRepository.save(interview);

        log.info("Interview {} updated to status {}", saved.getId(), saved.getStatus());

       /* notificationPort.notifyInterviewUpdated(
            interview.getCandidate().getEmail(), interview.getInterviewer().getEmail(),
            interview.getCandidate().getName(), interview.getInterviewer().getName(),
            "Status: " + saved.getStatus());*/

        return interviewMapper.toResponse(saved);
    }

    private void validateNoConflict(Long interviewerId, LocalDateTime scheduledTime, Long excludeInterviewId) {
        LocalDateTime interviewEnd = scheduledTime.plusHours(1);
        List<Interview> conflicts = interviewRepository.findConflictingInterviews(
                interviewerId, scheduledTime, interviewEnd, InterviewStatus.CANCELLED);

        if (excludeInterviewId != null) {
            conflicts = conflicts.stream()
                    .filter(i -> !i.getId().equals(excludeInterviewId))
                    .toList();
        }

        if (!conflicts.isEmpty()) {
            throw new IllegalArgumentException(AppConstants.INTERVIEW_CONFLICT);
        }
    }
}
