package com.tms.authenticationservice.feign;

import com.tms.authenticationservice.dto.RegisterRequest;
import com.tms.authenticationservice.dto.RegisterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service", url = "http://localhost:8081/USER-SERVICE/")
public interface UserServiceClient {

//    @GetMapping("/api/users/username/{username}")
//    UserResponse getUserByUsername(@PathVariable String username);

    @PostMapping("/api/users")
    ResponseEntity<RegisterResponse> registerUser(RegisterRequest request);
}