package com.flatirons.ims.mapper;

import org.springframework.stereotype.Component;

import com.flatirons.ims.dto.FeedbackResponse;
import com.flatirons.ims.dto.InterviewResponse;
import com.flatirons.ims.entity.Interview;
import com.flatirons.ims.enums.InterviewStatus;

@Component
public class InterviewMapper {

    private final FeedbackMapper feedbackMapper;

    public InterviewMapper(FeedbackMapper feedbackMapper) {
        this.feedbackMapper = feedbackMapper;
    }

    public InterviewResponse toResponse(Interview interview) {
        FeedbackResponse feedbackResponse = null;
        if (interview.getStatus() == InterviewStatus.COMPLETED && interview.getFeedback() != null) {
            feedbackResponse = feedbackMapper.toResponse(interview.getFeedback());
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
}
