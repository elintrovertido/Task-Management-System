package com.tms.taskservice.dto;

import com.tms.taskservice.model.Comment;
import com.tms.taskservice.utils.Priority;
import com.tms.taskservice.utils.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TaskResponse {

    private String id;

    private String projectId;

    private String title;

    private String description;

    private Priority priority;

    private LocalDate dueDate;

    private String assignedTo;

    private TaskStatus status;

    private List<Comment> comments;

    private LocalDateTime createdAt;
}