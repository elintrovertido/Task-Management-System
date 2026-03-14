package com.tms.authenticationservice.service;

import com.tms.authenticationservice.dto.LoginRequest;
import com.tms.authenticationservice.dto.LoginResponse;
import com.tms.authenticationservice.dto.RegisterRequest;
import com.tms.authenticationservice.dto.RegisterResponse;
import com.tms.authenticationservice.exception.DataProcessingException;
import com.tms.authenticationservice.exception.InvalidCredentialsException;
import com.tms.authenticationservice.exception.UserAlreadyExistException;
import com.tms.authenticationservice.feign.UserServiceClient;
import com.tms.authenticationservice.model.User;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;


    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        log.info("Entered into Register User : {}", registerRequest.getUserName());
        try {
            ResponseEntity<RegisterResponse> createdUser = userServiceClient.registerUser(registerRequest);
            return createdUser.getBody();
        } catch (UserAlreadyExistException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Database error occurred while creating user : {}", registerRequest.getUserName());
            throw new DataProcessingException("Database error occurred while creating user" + ex.getMessage());
        }
    }


    public LoginResponse loginUser(LoginRequest loginRequest) {
        try {

            User user = userServiceClient.getUserByEmail(loginRequest.getEmail());

            if (user == null) {
                throw new InvalidCredentialsException("Invalid username or password");
            }

            // Validate password
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new InvalidCredentialsException("Invalid username or password");
            }

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            // Generate JWT
            String token = jwtService.generateToken(user);

            return LoginResponse.builder()
                    .email(user.getUsername())
                    .accessToken(token)
                    .expiresIn(jwtService.getExpiration(token))
                    .build();

        } catch (FeignException.NotFound ex) {
            throw new InvalidCredentialsException("User not found");

        } catch (InvalidCredentialsException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new DataProcessingException("Error occurred : " + ex.getMessage());
        }
    }

    public User getProfile() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}