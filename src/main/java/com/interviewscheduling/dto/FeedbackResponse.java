package com.interviewscheduling.dto;

import com.interviewscheduling.entity.FeedbackStatus;

public class FeedbackResponse {

    private FeedbackStatus status;
    private String comments;

    public FeedbackResponse() {}

    public FeedbackResponse(FeedbackStatus status, String comments) {
        this.status = status;
        this.comments = comments;
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

