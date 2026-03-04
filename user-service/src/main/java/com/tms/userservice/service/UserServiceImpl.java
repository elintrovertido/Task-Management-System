package com.tms.userservice.service;

import com.tms.userservice.constants.Role;
import com.tms.userservice.dto.UserRequest;
import com.tms.userservice.dto.UserResponse;
import com.tms.userservice.exception.ResourceNotFoundException;
import com.tms.userservice.exception.UserAlreadyExistsException;
import com.tms.userservice.model.User;
import com.tms.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @CacheEvict(value = {"users", "usersList"}, key = "#id", allEntries = true)
    public UserResponse createUser(UserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Cacheable(value = "users", key = "#id")
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return mapToResponse(user);
    }

    @Cacheable(value = "usersList")
    public List<UserResponse> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @CacheEvict(value = {"users", "usersList"}, key = "#id", allEntries = true)
    public UserResponse updateUser(String id, UserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getEmail().equals(request.getEmail())) {

            userRepository.findByEmail(request.getEmail())
                    .ifPresent(existingUser -> {
                        throw new UserAlreadyExistsException(
                                "User already exists with email: " + request.getEmail());
                    });
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        if (StringUtils.hasText(request.getEmail())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user.setRole(Role.valueOf(request.getRole()));
        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

    @Override
    @CacheEvict(value = {"users", "usersList"}, key = "#id", allEntries = true)
    public void deleteUser(String id) {

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }


    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}