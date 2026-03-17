package com.flatirons.ims.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flatirons.ims.constants.AppConstants;
import com.flatirons.ims.dto.InterviewResponse;
import com.flatirons.ims.dto.ScheduleInterviewRequest;
import com.flatirons.ims.dto.UpdateInterviewRequest;
import com.flatirons.ims.service.InterviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(AppConstants.API_BASE)
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping(AppConstants.SCHEDULE)
    public ResponseEntity<InterviewResponse> scheduleInterview(@Valid @RequestBody ScheduleInterviewRequest request) {
        InterviewResponse interviewResponse = interviewService.scheduleInterview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(interviewResponse);
    }

    @PatchMapping(AppConstants.SCHEDULE)
    public ResponseEntity<InterviewResponse> updateInterview(@Valid @RequestBody UpdateInterviewRequest request) {
        InterviewResponse interviewResponse = interviewService.updateInterview(request);
        return ResponseEntity.ok(interviewResponse);
    }

    @GetMapping
    public ResponseEntity<List<InterviewResponse>> getInterviews(
            @RequestParam(required = false) String candidateName,
            @RequestParam(required = false) String interviewerName,
            Pageable pageable) {
        List<InterviewResponse> interviews = interviewService.getInterviews(candidateName, interviewerName, pageable);
        return ResponseEntity.ok(interviews);
    }
}