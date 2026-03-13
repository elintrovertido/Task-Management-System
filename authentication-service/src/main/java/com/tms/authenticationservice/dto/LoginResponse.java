package com.tms.authenticationservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class LoginResponse {
    private String userName;
    private String accessToken;
    private Date expiresIn;
}
