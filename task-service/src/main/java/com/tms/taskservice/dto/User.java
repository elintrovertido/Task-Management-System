package com.tms.taskservice.dto;

import com.tms.taskservice.utils.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    private String id;

    @Field(value = "name")
    private String userName;

    @Field(value = "email")
    private String email;

    @Field(value = "password")
    private String password;

    @Field(value = "role")
    private Roles roles;
}
