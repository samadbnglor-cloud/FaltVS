package com.interviewscheduling.dto;

import java.time.LocalDateTime;
import com.interviewscheduling.entity.InterviewStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.interviewscheduling.validation.ValidEnum;

@Data
public class UpdateInterviewRequest {
    @NotNull
    private Long id;
    @NotNull
    @ValidEnum(enumClass = InterviewStatus.class, message = "Invalid InterviewStatus. Allowed values: SCHEDULED, COMPLETED, CANCELLED")
    private InterviewStatus status;
    private LocalDateTime scheduledTime;
}
