package com.interviewscheduling.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.interviewscheduling.dto.FeedbackResponse;
import com.interviewscheduling.dto.InterviewResponse;
import com.interviewscheduling.dto.ScheduleInterviewRequest;
import com.interviewscheduling.dto.SubmitFeedbackRequest;
import com.interviewscheduling.dto.UpdateInterviewRequest;
import com.interviewscheduling.entity.*;
import com.interviewscheduling.exception.ResourceNotFoundException;
import com.interviewscheduling.repository.CandidateRepository;
import com.interviewscheduling.repository.FeedbackRepository;
import com.interviewscheduling.repository.InterviewRepository;
import com.interviewscheduling.repository.InterviewerRepository;
import com.interviewscheduling.repository.InterviewSpecifications;
import org.springframework.data.jpa.domain.Specification;
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
    private NotificationService notificationService;

    public InterviewService(InterviewRepository interviewRepository,
                            CandidateRepository candidateRepository,
                            InterviewerRepository interviewerRepository,
                            FeedbackRepository feedbackRepository) {
        this.interviewRepository = interviewRepository;
        this.candidateRepository = candidateRepository;
        this.interviewerRepository = interviewerRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    private Interview scheduleInterview(ScheduleInterviewRequest request) {
        // Validate scheduled time is not in the past
        if (request.getScheduledTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot schedule interview in the past");
        }

        Candidate candidate = candidateRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found"));
        Interviewer interviewer = interviewerRepository.findById(request.getInterviewerId())
                .orElseThrow(() -> new ResourceNotFoundException("Interviewer not found"));

        // Check for interviewer conflicts (assuming 1-hour interviews)
        LocalDateTime interviewEnd = request.getScheduledTime().plusHours(1);
        List<Interview> conflictingInterviews = interviewRepository.findConflictingInterviews(
                request.getInterviewerId(),
                request.getScheduledTime(),
                interviewEnd,
                InterviewStatus.CANCELLED
        );

        if (!conflictingInterviews.isEmpty()) {
            throw new IllegalArgumentException("Interviewer has a conflicting interview at this time");
        }

        Interview interview = new Interview(candidate, interviewer, request.getScheduledTime(), InterviewStatus.SCHEDULED);
        Interview saved = interviewRepository.save(interview);

        // Notify participants
       /* notificationService.notifyInterviewScheduled(
                candidate.getEmail(), interviewer.getEmail(),
                candidate.getName(), interviewer.getName(),
                request.getScheduledTime().toString()
        );*/

        return saved;
    }

    public InterviewResponse scheduleInterviewResponse(ScheduleInterviewRequest request) {
        Interview interview = scheduleInterview(request);
        return mapToResponse(interview);
    }

    public Feedback submitFeedback(SubmitFeedbackRequest request) {
        Interview interview = interviewRepository.findById(request.getInterviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found"));

        if (interview.getStatus() != InterviewStatus.SCHEDULED) {
            throw new IllegalArgumentException("Feedback can only be submitted for scheduled interviews");
        }

        Feedback feedback;
        if (interview.getFeedback() != null) {
            // Update existing feedback
            feedback = interview.getFeedback();
            feedback.setRating(request.getRating());
            feedback.setStatus(request.getStatus());
            feedback.setComments(request.getComments());
        } else {
            // Create new feedback
            feedback = new Feedback(interview, request.getRating(), request.getStatus(), request.getComments());
            interview.setFeedback(feedback);
        }
        interview.setStatus(InterviewStatus.COMPLETED); // Set to completed when feedback is given
        interviewRepository.save(interview);
        //Feedback savedFeedback = feedbackRepository.save(feedback);

        // Notify interviewer about feedback submission
       /* notificationService.notifyFeedbackSubmitted(
                interview.getInterviewer().getEmail(),
                interview.getInterviewer().getName(),
                interview.getCandidate().getName(),
                "Rating: " + request.getRating() + ", Status: " + request.getStatus() + ", Comments: " + request.getComments()
        );*/

        return feedback;
    }

    public FeedbackResponse submitFeedbackResponse(SubmitFeedbackRequest request) {
        Feedback saved = submitFeedback(request);
        return new FeedbackResponse(saved.getStatus(), saved.getComments());
    }

    public Page<InterviewResponse> getInterviews(String candidateName, String interviewerName, Pageable pageable) {
        Specification<Interview> spec = Specification.where(null);
        if (candidateName != null && !candidateName.isEmpty()) {
            spec = spec.and(InterviewSpecifications.hasCandidateName(candidateName));
        }
        if (interviewerName != null && !interviewerName.isEmpty()) {
            spec = spec.and(InterviewSpecifications.hasInterviewerName(interviewerName));
        }
       /* if (status != null) {
            spec = spec.and(InterviewSpecifications.hasStatus(status));
        }
        if (scheduledDate != null) {
            spec = spec.and(InterviewSpecifications.hasScheduledDate(scheduledDate));
        } */
        Page<Interview> interviews = interviewRepository.findAll(spec, pageable);
        return interviews.map(this::mapToResponse);
    }

    private InterviewResponse mapToResponse(Interview interview) {
        FeedbackResponse feedbackResponse = null;
        if (interview.getStatus() == InterviewStatus.COMPLETED && interview.getFeedback() != null) {
            Feedback feedback = interview.getFeedback();
            feedbackResponse = new FeedbackResponse(
                    feedback.getStatus(),
                    feedback.getComments()
            );
        }
        return new InterviewResponse(
                interview.getId(),
                interview.getCandidate().getName(),
                interview.getInterviewer().getName(),
                interview.getScheduledTime(),
                interview.getStatus(),
                feedbackResponse
        );
    }

    public InterviewResponse updateInterview(UpdateInterviewRequest request) {
        Interview interview = interviewRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found"));

        if (interview.getStatus() == InterviewStatus.COMPLETED) {
            throw new IllegalArgumentException("Cannot update completed interviews");
        }

        // Update status
        interview.setStatus(request.getStatus());

        // If rescheduling, validate and update time
        if (request.getScheduledTime() != null) {
            if (request.getScheduledTime().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Cannot schedule interview in the past");
            }
            interview.setScheduledTime(request.getScheduledTime());

            // Check for conflicts if rescheduling
            if (request.getStatus() != InterviewStatus.CANCELLED) {
                LocalDateTime interviewEnd = request.getScheduledTime().plusHours(1);
                List<Interview> conflictingInterviews = interviewRepository.findConflictingInterviews(
                        interview.getInterviewer().getId(),
                        request.getScheduledTime(),
                        interviewEnd,
                        InterviewStatus.CANCELLED
                );
                // Exclude the current interview from conflicts
                conflictingInterviews = conflictingInterviews.stream()
                        .filter(i -> !i.getId().equals(request.getId()))
                        .toList();

                if (!conflictingInterviews.isEmpty()) {
                    throw new IllegalArgumentException("Interviewer has a conflicting interview at this time");
                }
            }
        }

        Interview saved = interviewRepository.save(interview);

        // Notify participants about the update
      /*  String details = "Status changed to " + request.getStatus();
        if (request.getScheduledTime() != null) {
            details += ", rescheduled to " + request.getScheduledTime();
        }
        notificationService.notifyInterviewUpdated(
                saved.getCandidate().getEmail(), saved.getInterviewer().getEmail(),
                saved.getCandidate().getName(), saved.getInterviewer().getName(),
                details
        );*/

        return mapToResponse(saved);
    }

    // Additional methods as needed
}
