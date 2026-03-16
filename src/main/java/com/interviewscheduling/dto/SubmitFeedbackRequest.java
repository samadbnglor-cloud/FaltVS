package com.interviewscheduling.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SubmitFeedbackRequest {

    @NotNull
    private Long interviewId;

    @Min(1)
    @Max(5)
    private Integer rating;

    private String comments;

    // Constructors, getters, setters

    public SubmitFeedbackRequest() {}

    public SubmitFeedbackRequest(Long interviewId, Integer rating, String comments) {
        this.interviewId = interviewId;
        this.rating = rating;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}