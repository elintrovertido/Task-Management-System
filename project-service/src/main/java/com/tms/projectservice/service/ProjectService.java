package com.tms.projectservice.service;

import com.tms.projectservice.dto.ProjectRequest;
import com.tms.projectservice.dto.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(ProjectRequest request);

    ProjectResponse getProjectById(String id);

    List<ProjectResponse> getAllProjects();

    ProjectResponse updateProject(String id, ProjectRequest request);

    void deleteProject(String id);
}