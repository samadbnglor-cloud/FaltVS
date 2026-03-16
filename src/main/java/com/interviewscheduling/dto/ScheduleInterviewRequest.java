package com.interviewscheduling.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ScheduleInterviewRequest {

    @NotNull
    private Long candidateId;

    @NotNull
    private Long interviewerId;

    @NotNull
    private LocalDateTime scheduledTime;

    // Constructors, getters, setters

    public ScheduleInterviewRequest() {}

    public ScheduleInterviewRequest(Long candidateId, Long interviewerId, LocalDateTime scheduledTime) {
        this.candidateId = candidateId;
        this.interviewerId = interviewerId;
        this.scheduledTime = scheduledTime;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getInterviewerId() {
        return interviewerId;
    }

    public void setInterviewerId(Long interviewerId) {
        this.interviewerId = interviewerId;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}