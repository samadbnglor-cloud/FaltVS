package com.flatirons.ims.dto;

import java.time.LocalDateTime;

import com.flatirons.ims.enums.InterviewStatus;

public record InterviewResponse(Long id, String candidateName, String interviewerName, LocalDateTime scheduledTime, InterviewStatus status, FeedbackResponse feedback) {}