package com.tms.taskservice.dto;

import com.tms.taskservice.utils.Priority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {

    private String projectId;

    private String title;

    private String description;

    private Priority priority;

    private LocalDate dueDate;

    private String assignedTo;
}