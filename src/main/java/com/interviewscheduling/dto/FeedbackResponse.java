package com.interviewscheduling.dto;

import com.interviewscheduling.entity.FeedbackStatus;

public record FeedbackResponse(FeedbackStatus status, String comments) {}

