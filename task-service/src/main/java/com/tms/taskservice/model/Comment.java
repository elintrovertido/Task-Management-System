package com.tms.taskservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private String commentId;

    private String userId;

    private String message;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}