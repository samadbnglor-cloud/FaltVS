package com.flatirons.ims.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flatirons.ims.constants.AppConstants;
import com.flatirons.ims.dto.FeedbackResponse;
import com.flatirons.ims.dto.SubmitFeedbackRequest;
import com.flatirons.ims.service.feedback.FeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(AppConstants.API_BASE + AppConstants.FEEDBACK)
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackResponse> submitFeedback(@Valid @RequestBody SubmitFeedbackRequest request) {
        FeedbackResponse response = feedbackService.submitFeedbackResponse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
