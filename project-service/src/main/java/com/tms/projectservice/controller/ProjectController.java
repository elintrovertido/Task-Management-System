package com.tms.projectservice.controller;

import com.tms.projectservice.dto.ProjectRequest;
import com.tms.projectservice.dto.ProjectResponse;
import com.tms.projectservice.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @RequestBody ProjectRequest request) {

        return ResponseEntity.ok(projectService.createProject(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable String id) {

        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {

        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable String id,
            @RequestBody ProjectRequest request) {

        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {

        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}