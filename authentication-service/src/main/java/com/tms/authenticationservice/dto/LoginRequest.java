package com.tms.authenticationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String userName;
    @NotBlank(message = "password is required")
    private String password;
}
