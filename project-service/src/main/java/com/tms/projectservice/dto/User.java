package com.tms.projectservice.dto;

import com.tms.projectservice.utils.Roles;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class User {
    private String id;
    private String userName;
    private String email;
    private String password;
    private Roles roles;
}
