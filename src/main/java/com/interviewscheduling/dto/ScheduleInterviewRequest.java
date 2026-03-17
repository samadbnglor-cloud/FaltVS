package com.interviewscheduling.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ScheduleInterviewRequest {
    @NotNull
    private Long candidateId;
    @NotNull
    private Long interviewerId;
    @NotNull
    private LocalDateTime scheduledTime;
}