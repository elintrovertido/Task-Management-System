package com.tms.taskservice.dto;

import com.tms.taskservice.utils.Priority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {

    @NotBlank(message = "Project Id is required")
    private String projectId;

    @NotBlank(message = "Task Title is required")
    private String title;

    @NotBlank(message = "Task Description is required")
    private String description;

    @NotNull(message = "Task Priority is required")
    private Priority priority;

    @NotNull(message = "Task Due Date is required")
    @FutureOrPresent(message = "Due date must be in the future")
    private LocalDate dueDate;

    private String assignedTo;
}