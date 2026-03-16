package com.tms.projectservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AssignMembersRequest {

    @NotBlank
    private String operation;

    @NotEmpty(message = "Members are required")
    private List<String> members;
}