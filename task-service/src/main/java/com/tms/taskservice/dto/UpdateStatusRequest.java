package com.tms.taskservice.dto;

import com.tms.taskservice.utils.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusRequest {
    @NotNull(message = "Status is required")
    private TaskStatus status;
}