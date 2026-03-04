package com.tms.projectservice.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProjectResponse implements Serializable {

    private String id;

    private String name;

    private String description;

    private List<String> members;

    private String createdBy;

    private LocalDateTime createdAt;
}