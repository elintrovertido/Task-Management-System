package com.tms.taskservice.model;

import com.tms.taskservice.utils.Priority;
import com.tms.taskservice.utils.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "tasks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
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