package com.flatirons.ims.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
public class ScheduleInterviewRequest {

    @NotNull
    private Long candidateId;
    @NotNull
    private Long interviewerId;
    @NotNull
    private LocalDateTime scheduledTime;

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