package com.interviewscheduling.service;

import com.interviewscheduling.dto.InterviewResponse;
import com.interviewscheduling.dto.ScheduleInterviewRequest;
import com.interviewscheduling.dto.SubmitFeedbackRequest;
import com.interviewscheduling.entity.*;
import com.interviewscheduling.exception.ResourceNotFoundException;
import com.interviewscheduling.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final CandidateRepository candidateRepository;
    private final InterviewerRepository interviewerRepository;
    private final FeedbackRepository feedbackRepository;

    public InterviewService(InterviewRepository interviewRepository,
                            CandidateRepository candidateRepository,
                            InterviewerRepository interviewerRepository,
                            FeedbackRepository feedbackRepository) {
        this.interviewRepository = interviewRepository;
        this.candidateRepository = candidateRepository;
        this.interviewerRepository = interviewerRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public Interview scheduleInterview(ScheduleInterviewRequest request) {
        Candidate candidate = candidateRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found"));
        Interviewer interviewer = interviewerRepository.findById(request.getInterviewerId())
                .orElseThrow(() -> new ResourceNotFoundException("Interviewer not found"));

        Interview interview = new Interview(candidate, interviewer, request.getScheduledTime(), InterviewStatus.SCHEDULED);
        return interviewRepository.save(interview);
    }

    public Feedback submitFeedback(SubmitFeedbackRequest request) {
        Interview interview = interviewRepository.findById(request.getInterviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found"));

        if (interview.getStatus() != InterviewStatus.COMPLETED) {
            throw new IllegalArgumentException("Feedback can only be submitted for completed interviews");
        }

        Feedback feedback = new Feedback(interview, request.getRating(), request.getComments());
        interview.setFeedback(feedback);
        interviewRepository.save(interview);
        return feedbackRepository.save(feedback);
    }

    public Page<InterviewResponse> getInterviews(String candidateName, String interviewerName, Pageable pageable) {
        Page<Interview> interviews = interviewRepository.findByFilters(candidateName, interviewerName, pageable);
        return interviews.map(this::mapToResponse);
    }

    private InterviewResponse mapToResponse(Interview interview) {
        return new InterviewResponse(
                interview.getId(),
                interview.getCandidate().getName(),
                interview.getInterviewer().getName(),
                interview.getScheduledTime(),
                interview.getStatus()
        );
    }

    // Additional methods as needed
}