package com.tms.userservice.service;

import com.tms.userservice.constants.Role;
import com.tms.userservice.dto.UserRequest;
import com.tms.userservice.dto.UserResponse;
import com.tms.userservice.dto.ValidateRequest;
import com.tms.userservice.exception.DataProcessingException;
import com.tms.userservice.exception.ResourceNotFoundException;
import com.tms.userservice.exception.UserAlreadyExistsException;
import com.tms.userservice.model.User;
import com.tms.userservice.repository.UserRepository;
import com.tms.userservice.util.Roles;
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

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
        }

        if(userRepository.existsByUserName(request.getUserName())){
            throw new UserAlreadyExistsException("User already exists with userName: " + request.getUserName());
        }

        User user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Roles.valueOf(request.getRole()))
                .build();

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    public User getUserByEmail(String email) {
        try {
            if (!StringUtils.hasText(email)) {
                throw new IllegalArgumentException("Email is requred");
            }

            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User Not Found with Email: " + email));

        } catch (Exception ex) {
            throw new DataProcessingException("Failed to Process : " + ex.getMessage());
        }
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

        if (!user.getUserName().equals(request.getUserName())) {

            boolean userExists = userRepository.existsByUserName(request.getUserName());
            if(userExists) throw new UserAlreadyExistsException(
                    "User already exists with userName: " + request.getUserName());

        }

        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());

        if (StringUtils.hasText(request.getEmail())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user.setRoles(Roles.valueOf(request.getRole()));
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
                .userName(user.getUserName())
                .email(user.getEmail())
                .role(user.getRoles().name())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}