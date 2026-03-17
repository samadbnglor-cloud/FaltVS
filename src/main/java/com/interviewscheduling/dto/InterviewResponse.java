package com.interviewscheduling.dto;

import java.time.LocalDateTime;

import com.interviewscheduling.entity.InterviewStatus;

public record InterviewResponse(Long id, String candidateName, String interviewerName, LocalDateTime scheduledTime, InterviewStatus status, FeedbackResponse feedback) {}