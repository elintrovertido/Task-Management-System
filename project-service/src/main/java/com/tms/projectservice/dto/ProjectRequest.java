package com.tms.projectservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProjectRequest {

    private String name;

    private String description;

    private List<String> members;

    private String createdBy;
}