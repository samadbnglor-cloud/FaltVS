package com.interviewscheduling.dto;

import com.interviewscheduling.entity.FeedbackStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.interviewscheduling.validation.ValidEnum;

@Data
public class SubmitFeedbackRequest {
    @NotNull
    private Long interviewId;
    @Min(1)
    @Max(5)
    private Integer rating;
    @NotNull
    @ValidEnum(enumClass = FeedbackStatus.class, message = "Invalid FeedbackStatus. Allowed values: HIRED, REJECTED, ON_HOLD, FURTHER_DISCUSSION")
    private FeedbackStatus status;
    @NotBlank
    private String comments;
}