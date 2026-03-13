package com.tms.authenticationservice.controller;

import com.tms.authenticationservice.dto.LoginRequest;
import com.tms.authenticationservice.dto.LoginResponse;
import com.tms.authenticationservice.dto.RegisterRequest;
import com.tms.authenticationservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(
//            @RequestBody LoginRequest request) {
//
//        return ResponseEntity.ok(authService.login(request));
//    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {

        authService.registerUser(request);

        return ResponseEntity.ok("User registered successfully");
    }
}