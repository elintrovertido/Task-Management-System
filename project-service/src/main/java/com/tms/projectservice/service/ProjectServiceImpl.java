package com.tms.projectservice.service;

import com.tms.projectservice.dto.ProjectRequest;
import com.tms.projectservice.dto.ProjectResponse;
import com.tms.projectservice.exception.ResourceNotFoundException;
import com.tms.projectservice.model.Project;
import com.tms.projectservice.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    @CachePut(value = {"projects", "projectsList"} , key = "#id")
    public ProjectResponse createProject(ProjectRequest request) {

        try {
            log.info("Creating project with name: {}", request.getName());

            Project project = Project.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .members(request.getMembers())
                    .build();

            Project saved = projectRepository.save(project);

            log.info("Project created successfully with id: {}", saved.getId());

            return mapToResponse(saved);

        } catch (Exception e) {
            log.error("Error occurred while creating project", e);
            throw new RuntimeException("Failed to create project");
        }
    }

    @Override
    @Cacheable(value = "projects", key = "#id")
    public ProjectResponse getProjectById(String id) {

        try {
            log.info("Fetching project with id: {}", id);

            Project project = projectRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

            return mapToResponse(project);

        } catch (ResourceNotFoundException e) {
            log.warn(e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("Error fetching project with id: {}", id, e);
            throw new RuntimeException("Failed to fetch project");
        }
    }

    @Override
    @Cacheable(value = "projectsList")
    public List<ProjectResponse> getAllProjects() {

        try {
            log.info("Fetching all projects");

            List<ProjectResponse> projects = projectRepository.findAll()
                    .stream()
                    .map(this::mapToResponse)
                    .toList();

            log.info("Total projects fetched: {}", projects.size());

            return projects;

        } catch (Exception e) {
            log.error("Error fetching all projects", e);
            throw new RuntimeException("Failed to fetch projects");
        }
    }

    @Override
    @CacheEvict(value = {"projects", "projectsList"} , allEntries = true)
    public ProjectResponse updateProject(String id, ProjectRequest request) {

        try {
            log.info("Updating project with id: {}", id);

            Project project = projectRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

            project.setName(request.getName());
            project.setDescription(request.getDescription());
            project.setMembers(request.getMembers());

            Project updated = projectRepository.save(project);

            log.info("Project updated successfully with id: {}", id);

            return mapToResponse(updated);

        } catch (ResourceNotFoundException e) {
            log.warn(e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("Error updating project with id: {}", id, e);
            throw new RuntimeException("Failed to update project");
        }
    }

    @Override
    @CacheEvict(value = {"projects", "projectsList"} , key = "#id")
    public void deleteProject(String id) {

        try {
            log.info("Deleting project with id: {}", id);

            Project project = projectRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

            projectRepository.delete(project);

            log.info("Project deleted successfully with id: {}", id);

        } catch (ResourceNotFoundException e) {
            log.warn(e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("Error deleting project with id: {}", id, e);
            throw new RuntimeException("Failed to delete project");
        }
    }

    private ProjectResponse mapToResponse(Project project) {

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .members(project.getMembers())
                .createdBy(project.getCreatedBy())
                .createdAt(project.getCreatedAt())
                .build();
    }
}