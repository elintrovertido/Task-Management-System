package com.tms.userservice.service;

import com.tms.userservice.dto.UserRequest;
import com.tms.userservice.dto.UserResponse;
import com.tms.userservice.dto.ValidateRequest;
import com.tms.userservice.model.User;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);

    User getUserByEmail(String email);

    UserResponse getUserById(String id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(String id, UserRequest request);

    void deleteUser(String id);
}
