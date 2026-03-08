package com.tms.taskservice.service;

import com.tms.taskservice.constants.ApplicationConstants;
import com.tms.taskservice.dto.CommentRequest;
import com.tms.taskservice.dto.TaskRequest;
import com.tms.taskservice.dto.TaskResponse;
import com.tms.taskservice.exception.ResourceNotFoundException;
import com.tms.taskservice.exception.TaskException;
import com.tms.taskservice.model.Comment;
import com.tms.taskservice.model.Task;
import com.tms.taskservice.repository.TaskRepository;
import com.tms.taskservice.service.TaskService;
import com.tms.taskservice.utils.TaskStatus;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;

    @Override
    public TaskResponse createTask(TaskRequest request) {
        log.info("Creating task for projectId: {}", request.getProjectId());

        try {

            Task task = Task.builder()
                    .projectId(request.getProjectId())
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .priority(request.getPriority())
                    .dueDate(request.getDueDate())
                    .assignedTo(request.getAssignedTo())
                    .status(TaskStatus.TODO)
                    .comments(new ArrayList<>())
                    .createdAt(LocalDateTime.now())
                    .build();

            Task savedTask = taskRepository.save(task);

            log.info("Task created successfully with id: {}", savedTask.getId());

            return mapToResponse(savedTask);

        } catch (Exception e) {
            log.error("Error occurred while creating task", e);
            throw new TaskException("Failed to create task");
        }
    }

    @Override
    public TaskResponse updateTask(String id, TaskRequest request) {

        log.info("Updating task with id: {}", id);

        try {

            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ApplicationConstants.TASK_NOT_FOUND));

            task.setTitle(request.getTitle());
            task.setDescription(request.getDescription());
            task.setPriority(request.getPriority());
            task.setDueDate(request.getDueDate());
            task.setAssignedTo(request.getAssignedTo());

            Task updatedTask = taskRepository.save(task);

            log.info("Task updated successfully with id: {}", id);

            return mapToResponse(updatedTask);

        } catch (Exception e) {
            log.error("Error updating task with id: {}", id, e);
            throw new TaskException("Failed to update task");
        }
    }

    @Override
    public TaskResponse assignTask(String taskId, String userId) {

        log.info("Assigning task {} to user {}", taskId, userId);

        try {

            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new ResourceNotFoundException(ApplicationConstants.TASK_NOT_FOUND));

            task.setAssignedTo(userId);

            Task updatedTask = taskRepository.save(task);

            log.info("Task {} assigned to user {}", taskId, userId);

            return mapToResponse(updatedTask);

        } catch (Exception e) {
            log.error("Error assigning task {}", taskId, e);
            throw new TaskException("Failed to assign task");
        }
    }

    @Override
    public TaskResponse updateStatus(String taskId, TaskStatus status) {

        log.info("Updating status for task {} to {}", taskId, status);

        try {

            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new ResourceNotFoundException(ApplicationConstants.TASK_NOT_FOUND));

            task.setStatus(status);

            Task updatedTask = taskRepository.save(task);

            log.info("Task {} status updated to {}", taskId, status);

            return mapToResponse(updatedTask);

        } catch (Exception e) {
            log.error("Error updating status for task {}", taskId, e);
            throw new TaskException("Failed to update task status");
        }
    }

    @Override
    public void deleteTask(String id) {

        log.info("Deleting task with id {}", id);

        try {

            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ApplicationConstants.TASK_NOT_FOUND));

            taskRepository.delete(task);

            log.info("Task deleted successfully with id {}", id);

        } catch (Exception e) {
            log.error("Error deleting task with id {}", id, e);
            throw new TaskException("Failed to delete task");
        }
    }

    @Override
    public List<TaskResponse> getTasksByProject(String projectId) {

        log.info("Fetching tasks for project {}", projectId);

        try {

            List<Task> tasks = taskRepository.findByProjectId(projectId);

            return tasks.stream()
                    .map(this::mapToResponse)
                    .toList();

        } catch (Exception e) {
            log.error("Error fetching tasks for project {}", projectId, e);
            throw new TaskException("Failed to fetch tasks");
        }
    }

    @Override
    public List<TaskResponse> getTasksByUser(String userId) {

        log.info("Fetching tasks for user {}", userId);

        try {

            List<Task> tasks = taskRepository.findByAssignedTo(userId);

            return tasks.stream()
                    .map(this::mapToResponse)
                    .toList();

        } catch (Exception e) {
            log.error("Error fetching tasks for user {}", userId, e);
            throw new TaskException("Failed to fetch tasks");
        }
    }

    @Override
    public Comment addComment(String taskId, CommentRequest commentRequest) {

        log.info("Adding comment to task {}", taskId);

        try {

            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new ResourceNotFoundException(ApplicationConstants.TASK_NOT_FOUND));

            Comment comment = Comment.builder()
                    .commentId(UUID.randomUUID().toString())
                    .userId(commentRequest.getUserId())
                    .message(commentRequest.getMessage())
                    .createdAt(LocalDateTime.now())
                    .build();

            task.getComments().add(comment);

            taskRepository.save(task);

            log.info("Comment added successfully to task {}", taskId);

            return comment;

        } catch (Exception e) {
            log.error("Error adding comment to task {}", taskId, e);
            throw new TaskException("Failed to add comment");
        }
    }

    public void deleteComment(String taskId,String commentId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(ApplicationConstants.TASK_NOT_FOUND));
        List<Comment> commentList = task.getComments()
                .stream()
                .filter(comment -> !comment.getCommentId().equalsIgnoreCase(commentId))
                .toList();

        task.setComments(commentList);
        taskRepository.save(task);
    };

    private TaskResponse mapToResponse(Task task) {

        return TaskResponse.builder()
                .id(task.getId())
                .projectId(task.getProjectId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .assignedTo(task.getAssignedTo())
                .status(task.getStatus())
                .comments(task.getComments())
                .createdAt(task.getCreatedAt())
                .build();
    }
}