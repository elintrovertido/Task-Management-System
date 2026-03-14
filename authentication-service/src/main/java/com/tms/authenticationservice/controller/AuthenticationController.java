package com.tms.authenticationservice.controller;

import com.tms.authenticationservice.dto.LoginRequest;
import com.tms.authenticationservice.dto.LoginResponse;
import com.tms.authenticationservice.dto.RegisterRequest;
import com.tms.authenticationservice.model.User;
import com.tms.authenticationservice.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest
                    request) {

        return ResponseEntity.ok(authService.loginUser(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        return ResponseEntity.ok(authService.getProfile());
    }
}