package com.flatirons.ims.mapper;

import org.springframework.stereotype.Component;

import com.flatirons.ims.dto.FeedbackResponse;
import com.flatirons.ims.dto.SubmitFeedbackRequest;
import com.flatirons.ims.entity.Feedback;

@Component
public class FeedbackMapper {

    public FeedbackResponse toResponse(Feedback feedback) {
        if (feedback == null) return null;
        return new FeedbackResponse(feedback.getStatus(), feedback.getComments());
    }

    public void mapRequestToFeedback(SubmitFeedbackRequest request, Feedback feedback) {
        feedback.setStatus(request.getStatus());
        feedback.setComments(request.getComments());
        feedback.setRating(request.getRating());
    }
}
