package com.tms.userservice.model;

import com.tms.userservice.constants.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@Getter
@Setter
@Data
@Builder
public class User extends Auditable {
    @Id
    private String id;

    @Field(value = "name")
    private String name;

    @Field(value = "email")
    private String email;

    @Field(value = "password")
    private String password;

    @Field(value = "role")
    private Role role;
}
