package com.tms.taskservice.controller;

import com.tms.taskservice.dto.CommentRequest;
import com.tms.taskservice.dto.TaskRequest;
import com.tms.taskservice.dto.TaskResponse;
import com.tms.taskservice.dto.UpdateStatusRequest;
import com.tms.taskservice.model.Comment;
import com.tms.taskservice.service.TaskService;
import com.tms.taskservice.utils.TaskStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable String id,
            @Valid @RequestBody TaskRequest request) {
        TaskResponse response = taskService.updateTask(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponse>> getTasksByProject(@PathVariable String projectId) {
        List<TaskResponse> tasks = taskService.getTasksByProject(projectId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponse>> getTasksByUser(@PathVariable String userId) {
        List<TaskResponse> tasks = taskService.getTasksByUser(userId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskResponse> assignTask(
            @PathVariable String taskId,
            @PathVariable String userId) {
        TaskResponse response = taskService.assignTask(taskId, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{taskId}/assignToMe")
    public ResponseEntity<TaskResponse> assignTask(
            @PathVariable String taskId) {
        TaskResponse response = taskService.assignToMe(taskId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskResponse> updateStatus(
            @PathVariable String taskId,
            @RequestBody @Valid UpdateStatusRequest statusRequest) {
        TaskResponse response = taskService.updateStatus(taskId, statusRequest.getStatus());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{taskId}/comments")
    public ResponseEntity<Comment> addComment(
            @PathVariable String taskId,
            @Valid @RequestBody CommentRequest request) {
        Comment comment = taskService.addComment(taskId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @DeleteMapping("/{taskId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable String taskId,
            @PathVariable String commentId) {
        taskService.deleteComment(taskId, commentId);
        return ResponseEntity.noContent().build();
    }
}