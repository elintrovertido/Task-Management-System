package com.tms.taskservice.dto;

import lombok.Data;

@Data
public class CommentRequest {

    private String userId;

    private String message;

}