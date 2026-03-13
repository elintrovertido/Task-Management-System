package com.tms.authenticationservice.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class RegisterResponse implements Serializable {

    private String id;

    private String name;

    private String email;

    private String role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}