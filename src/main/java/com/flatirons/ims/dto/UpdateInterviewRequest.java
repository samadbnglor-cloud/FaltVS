package com.flatirons.ims.dto;

import java.time.LocalDateTime;

import com.flatirons.ims.enums.InterviewStatus;
import com.flatirons.ims.validation.ValidEnum;

import jakarta.validation.constraints.NotNull;
public class UpdateInterviewRequest {

    @NotNull
    private Long id;
    @NotNull
    @ValidEnum(enumClass = InterviewStatus.class, message = "Invalid InterviewStatus. Allowed values: SCHEDULED, CANCELLED")
    private InterviewStatus status;
    private LocalDateTime scheduledTime;

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
