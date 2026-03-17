package com.flatirons.ims.service.feedback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flatirons.ims.constants.AppConstants;
import com.flatirons.ims.dto.FeedbackResponse;
import com.flatirons.ims.dto.SubmitFeedbackRequest;
import com.flatirons.ims.entity.Feedback;
import com.flatirons.ims.entity.Interview;
import com.flatirons.ims.exception.ResourceNotFoundException;
import com.flatirons.ims.mapper.FeedbackMapper;
import com.flatirons.ims.repository.InterviewRepository;
import com.flatirons.ims.service.NotificationPort;

@Service
@Transactional
public class FeedbackService {

    private static final Logger log = LoggerFactory.getLogger(FeedbackService.class);

    private final InterviewRepository interviewRepository;
    private final FeedbackMapper feedbackMapper;
    private final NotificationPort notificationPort;

    public FeedbackService(InterviewRepository interviewRepository,
                           FeedbackMapper feedbackMapper,
                           NotificationPort notificationPort) {
        this.interviewRepository = interviewRepository;
        this.feedbackMapper = feedbackMapper;
        this.notificationPort = notificationPort;
    }

    public FeedbackResponse submitFeedbackResponse(SubmitFeedbackRequest request) {
        Interview interview = interviewRepository.findById(request.getInterviewId())
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.INTERVIEW_NOT_FOUND));

        Feedback feedback = interview.getFeedback();
        if (feedback == null) {
            feedback = new Feedback();
        }
        feedbackMapper.mapRequestToFeedback(request, feedback);

        // Domain method handles status transition + bidirectional link
        interview.attachFeedback(feedback);

        interviewRepository.save(interview);

        log.info("Feedback submitted for interview {}", interview.getId());

        /*notificationPort.notifyFeedbackSubmitted(
            interview.getInterviewer().getEmail(),
            interview.getInterviewer().getName(),
            interview.getCandidate().getName(),
            feedback.getStatus().name());*/

        return feedbackMapper.toResponse(feedback);
    }
}
