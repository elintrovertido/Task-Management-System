package com.tms.authenticationservice.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String email;
    private String accessToken;
    private Date expiresIn;
}
