package com.interviewscheduling.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interviewscheduling.dto.FeedbackResponse;
import com.interviewscheduling.dto.InterviewResponse;
import com.interviewscheduling.dto.ScheduleInterviewRequest;
import com.interviewscheduling.dto.SubmitFeedbackRequest;
import com.interviewscheduling.dto.UpdateInterviewRequest;
import com.interviewscheduling.entity.InterviewStatus;
import com.interviewscheduling.service.InterviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<InterviewResponse> scheduleInterview(@Valid @RequestBody ScheduleInterviewRequest request) {
        InterviewResponse interviewResponse = interviewService.scheduleInterviewResponse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(interviewResponse);
    }

    @PostMapping("/feedback")
    public ResponseEntity<FeedbackResponse> submitFeedback(@Valid @RequestBody SubmitFeedbackRequest request) {
        FeedbackResponse feedbackResponse = interviewService.submitFeedbackResponse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackResponse);
    }

    @PatchMapping("/schedule")
    public ResponseEntity<InterviewResponse> updateInterview(@Valid @RequestBody UpdateInterviewRequest request) {
        InterviewResponse interviewResponse = interviewService.updateInterview(request);
        return ResponseEntity.ok(interviewResponse);
    }

    @GetMapping
    public ResponseEntity<List<InterviewResponse>> getInterviews(
            @RequestParam(required = false) String candidateName,
            @RequestParam(required = false) String interviewerName,
         //   @RequestParam(required = false) InterviewStatus status,
         //   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate scheduledDate,
            Pageable pageable) {
        Page<InterviewResponse> interviews = interviewService.getInterviews(candidateName, interviewerName, pageable);
        return ResponseEntity.ok(interviews.getContent());
    }
}