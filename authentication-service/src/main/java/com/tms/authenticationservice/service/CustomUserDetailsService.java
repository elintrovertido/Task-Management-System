//package com.tms.authenticationservice.service;
//
//import com.tms.authenticationservice.feign.UserServiceClient;
//import com.tms.authenticationservice.model.CustomUserDetails;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserServiceClient userServiceClient;
//
//    public CustomUserDetailsService(UserServiceClient userServiceClient) {
//        this.userServiceClient = userServiceClient;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username)
//            throws UsernameNotFoundException {
//
//        try {
//
//            log.info("Fetching user {} from user-service", username);
//
//            UserResponse user = userServiceClient.getUserByUsername(username);
//
//            if (user == null) {
//                throw new UsernameNotFoundException("User not found");
//            }
//
//            return new CustomUserDetails(user);
//
//        } catch (Exception ex) {
//
//            log.error("Error fetching user {}", ex.getMessage());
//            throw new UsernameNotFoundException("User not found");
//
//        }
//    }
//}