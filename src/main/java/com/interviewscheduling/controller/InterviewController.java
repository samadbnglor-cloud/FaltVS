package com.interviewscheduling.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviewscheduling.dto.InterviewResponse;
import com.interviewscheduling.dto.ScheduleInterviewRequest;
import com.interviewscheduling.dto.SubmitFeedbackRequest;
import com.interviewscheduling.entity.Feedback;
import com.interviewscheduling.entity.Interview;
import com.interviewscheduling.service.InterviewService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/interviews")
@Tag(name = "Interview Management", description = "APIs for managing interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<Interview> scheduleInterview(@Valid @RequestBody ScheduleInterviewRequest request) {
        Interview interview = interviewService.scheduleInterview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(interview);
    }

    @PostMapping("/feedback")
    public ResponseEntity<Feedback> submitFeedback(@Valid @RequestBody SubmitFeedbackRequest request) {
        Feedback feedback = interviewService.submitFeedback(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedback);
    }

    @GetMapping
    public ResponseEntity<Page<InterviewResponse>> getInterviews(
            @RequestParam(required = false) String candidateName,
            @RequestParam(required = false) String interviewerName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InterviewResponse> interviews = interviewService.getInterviews(candidateName, interviewerName, pageable);
        return ResponseEntity.ok(interviews);
    }
}