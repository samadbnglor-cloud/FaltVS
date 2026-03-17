package com.flatirons.ims.dto;

import com.flatirons.ims.enums.FeedbackStatus;

public record FeedbackResponse(FeedbackStatus status, String comments) {}

