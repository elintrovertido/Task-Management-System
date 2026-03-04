package com.tms.userservice.service;

import com.tms.userservice.dto.UserRequest;
import com.tms.userservice.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);

    UserResponse getUserById(String id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(String id, UserRequest request);

    void deleteUser(String id);
}
