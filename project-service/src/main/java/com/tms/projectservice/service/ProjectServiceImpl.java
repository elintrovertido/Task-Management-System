package com.tms.projectservice.service;

import com.tms.projectservice.dto.AssignMembersRequest;
import com.tms.projectservice.dto.ProjectRequest;
import com.tms.projectservice.dto.ProjectResponse;
import com.tms.projectservice.exception.DataProcessingException;
import com.tms.projectservice.exception.ResourceNotFoundException;
import com.tms.projectservice.model.Project;
import com.tms.projectservice.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    private final ProjectRepository projectRepository;

    @Override
    @Caching(
            put = { @CachePut(value = "project", key = "#result.id") },
            evict = { @CacheEvict(value = "projectList", allEntries = true) }
    )
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
            throw new DataProcessingException(e.getMessage());
        }
    }

    @Override
    @Cacheable(value = "project", key = "#id")
    public ProjectResponse getProjectById(String id) {

        try {
            log.info("Fetching project with id: {}", id);

            Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

            return mapToResponse(project);

        } catch (ResourceNotFoundException e) {
            log.warn(e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("Error fetching project with id: {}", id, e);
            throw new DataProcessingException(e.getMessage());
        }
    }

    @Override
    @Cacheable(value = "projectList")
    public List<ProjectResponse> getAllProjects() {

        try {
            log.info("Fetching all projects");

            List<ProjectResponse> projects = projectRepository.findAll().stream().map(this::mapToResponse).toList();

            log.info("Total projects fetched: {}", projects.size());

            return projects;

        } catch (Exception e) {
            log.error("Error fetching all projects", e);
            throw new DataProcessingException(e.getMessage());
        }
    }

    @Override
    @Caching(
            put = { @CachePut(value = "project", key = "#id") },
            evict = { @CacheEvict(value = "projectList", allEntries = true) }
    )
    public ProjectResponse updateProject(String id, ProjectRequest request) {

        try {
            log.info("Updating project with id: {}", id);

            Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

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
            throw new DataProcessingException(e.getMessage());
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "project", key = "#id"),
            @CacheEvict(value = "projectList", allEntries = true)
    })
    public void deleteProject(String id) {

        try {
            log.info("Deleting project with id: {}", id);

            Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

            projectRepository.delete(project);

            log.info("Project deleted successfully with id: {}", id);

        } catch (ResourceNotFoundException e) {
            log.warn(e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("Error deleting project with id: {}", id, e);
            throw new DataProcessingException(e.getMessage());
        }
    }

    @Override
    @Caching(
            put = { @CachePut(value = "project", key = "#projectId") },
            evict = { @CacheEvict(value = "projectList", allEntries = true) }
    )
    public ProjectResponse assignUsersToProject(String projectId, AssignMembersRequest assignMembersRequest) {

        try {
            log.info("Assigning users {} to project {}", assignMembersRequest.getMembers(), projectId);

            Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));
            List<String> existingMembers = project.getMembers() != null
                    ? project.getMembers()
                    : new ArrayList<>();

            if (ADD.equalsIgnoreCase(assignMembersRequest.getOperation())) {
                existingMembers.addAll(assignMembersRequest.getMembers());
                List<String> updatedMembers = existingMembers.stream()
                        .distinct()
                        .toList();
                project.setMembers(updatedMembers);
            } else if (REMOVE.equalsIgnoreCase(assignMembersRequest.getOperation())) {
                List<String> updatedMembers = existingMembers.stream()
                        .filter(member -> !assignMembersRequest.getMembers().contains(member))
                        .toList();
                project.setMembers(updatedMembers);
            }

            Project updatedProject = projectRepository.save(project);
            log.info("Users successfully assigned to project {}", projectId);

            return mapToResponse(updatedProject);
        } catch (ResourceNotFoundException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error assigning users to project {}", projectId, e);
            throw new DataProcessingException(e.getMessage());
        }
    }

    private ProjectResponse mapToResponse(Project project) {

        return ProjectResponse.builder().id(project.getId()).name(project.getName()).description(project.getDescription()).members(project.getMembers()).createdBy(project.getCreatedBy()).createdAt(project.getCreatedAt()).build();
    }
}