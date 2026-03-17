package com.interviewscheduling.dto;

import java.time.LocalDateTime;

import com.interviewscheduling.entity.InterviewStatus;

public class InterviewResponse {

    private Long id;
    private String candidateName;
    private String interviewerName;
    private LocalDateTime scheduledTime;
    private InterviewStatus status;
    private FeedbackResponse feedback;

    // Constructors, getters, setters

    public InterviewResponse() {}

    public InterviewResponse(Long id, String candidateName, String interviewerName, LocalDateTime scheduledTime, InterviewStatus status) {
        this.id = id;
        this.candidateName = candidateName;
        this.interviewerName = interviewerName;
        this.scheduledTime = scheduledTime;
        this.status = status;
    }

    public InterviewResponse(Long id, String candidateName, String interviewerName, LocalDateTime scheduledTime, InterviewStatus status, FeedbackResponse feedback) {
        this.id = id;
        this.candidateName = candidateName;
        this.interviewerName = interviewerName;
        this.scheduledTime = scheduledTime;
        this.status = status;
        this.feedback = feedback;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getInterviewerName() {
        return interviewerName;
    }

    public void setInterviewerName(String interviewerName) {
        this.interviewerName = interviewerName;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }

    public FeedbackResponse getFeedback() {
        return feedback;
    }

    public void setFeedback(FeedbackResponse feedback) {
        this.feedback = feedback;
    }
}