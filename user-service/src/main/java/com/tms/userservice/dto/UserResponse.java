package com.tms.userservice.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse implements Serializable {

    private String id;

    private String userName;

    private String email;

    private String role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}