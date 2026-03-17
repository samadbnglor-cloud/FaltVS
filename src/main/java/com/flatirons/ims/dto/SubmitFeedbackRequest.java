package com.flatirons.ims.dto;

import com.flatirons.ims.enums.FeedbackStatus;
import com.flatirons.ims.validation.ValidEnum;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    public Long getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public FeedbackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedbackStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}