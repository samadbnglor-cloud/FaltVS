package com.interviewscheduling.dto;

import java.time.LocalDateTime;

import com.interviewscheduling.entity.InterviewStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateInterviewRequest {

    @NotNull
    private Long id;

    @NotNull
    private InterviewStatus status;

    private LocalDateTime scheduledTime;

    // Constructors, getters, setters

    public UpdateInterviewRequest() {}

    public UpdateInterviewRequest(Long id, InterviewStatus status, LocalDateTime scheduledTime) {
        this.id = id;
        this.status = status;
        this.scheduledTime = scheduledTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
