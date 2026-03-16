package com.tms.projectservice.controller;

import com.tms.projectservice.dto.AssignMembersRequest;
import com.tms.projectservice.dto.ProjectRequest;
import com.tms.projectservice.dto.ProjectResponse;
import com.tms.projectservice.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER')")
    public ResponseEntity<ProjectResponse> createProject(
            @RequestBody ProjectRequest request) {

        return ResponseEntity.ok(projectService.createProject(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER','USER')")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable String id) {

        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER')")
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {

        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER')")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable String id,
            @RequestBody ProjectRequest request) {

        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {

        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{projectId}/members")
    @PreAuthorize("hasAnyRole('ADMIN','PROJECT_MANAGER')")
    public ResponseEntity<ProjectResponse> assignUsersToProject(
            @PathVariable String projectId,
            @Valid @RequestBody AssignMembersRequest assignMembersRequest) {

        return ResponseEntity.ok(projectService.assignUsersToProject(projectId, assignMembersRequest));
    }
}