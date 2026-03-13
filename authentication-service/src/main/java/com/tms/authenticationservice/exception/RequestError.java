package com.tms.authenticationservice.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestError {
    private String field;
    private String message;
}
