package com.tms.authenticationservice.feign;

import com.tms.authenticationservice.dto.RegisterRequest;
import com.tms.authenticationservice.dto.RegisterResponse;
import com.tms.authenticationservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://localhost:8081/USER-SERVICE/")
public interface UserServiceClient {

    @GetMapping("/api/users/email/{email}")
    User getUserByEmail(@PathVariable String email);

    @PostMapping("/api/users")
    ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request);
}