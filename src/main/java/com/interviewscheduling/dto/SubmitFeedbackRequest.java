package com.interviewscheduling.dto;

import com.interviewscheduling.entity.FeedbackStatus;

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
    private FeedbackStatus status;

    @NotBlank
    private String comments;

    // Constructors, getters, setters

    public SubmitFeedbackRequest() {}

    public SubmitFeedbackRequest(Long interviewId, Integer rating, FeedbackStatus status, String comments) {
        this.interviewId = interviewId;
        this.rating = rating;
        this.status = status;
        this.comments = comments;
    }

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