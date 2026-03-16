package com.tms.taskservice.dto;

import com.tms.taskservice.utils.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private String id;

    private String userName;

    private String email;

    private String password;

    private Roles roles;
}
