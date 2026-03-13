package com.tms.authenticationservice.service;

import com.tms.authenticationservice.dto.LoginRequest;
import com.tms.authenticationservice.dto.LoginResponse;
import com.tms.authenticationservice.dto.RegisterRequest;
import com.tms.authenticationservice.dto.RegisterResponse;
import com.tms.authenticationservice.exception.DataProcessingException;
import com.tms.authenticationservice.exception.UserAlreadyExistException;
import com.tms.authenticationservice.feign.UserServiceClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationService {

    private final UserServiceClient userServiceClient;


    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        log.info("Entered into Register User : {}", registerRequest.getName());
        try {
            ResponseEntity<RegisterResponse> createdUser = userServiceClient.registerUser(registerRequest);
            return createdUser.getBody();
        } catch (UserAlreadyExistException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Database error occurred while creating user : {}", registerRequest.getName());
            throw new DataProcessingException("Database error occurred while creating user" + ex.getMessage());
        }
    }



//    public LoginResponse loginUser(LoginRequest loginRequest) {
//        try {
//            Authentication authenticate = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
//            );
//            AuthUser authUser =(AuthUser) authenticate.getPrincipal();
//
//            String token = jwtService.generateToken(authUser);
//            return LoginResponse.builder()
//                    .userName(loginRequest.getUserName())
//                    .accessToken(token)
//                    .expiresIn(jwtService.getExpiration(token))
//                    .build();
//        } catch (AuthenticationException ex) {
//            throw new InvalidCredentialsException(ex.getMessage());
//        } catch (Exception ex) {
//            throw new DataProcessingException("Error occurred : " + ex.getMessage());
//        }
//    }
}