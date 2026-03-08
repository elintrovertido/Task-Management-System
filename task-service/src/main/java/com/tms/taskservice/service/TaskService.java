package com.tms.taskservice.service;

import com.tms.taskservice.dto.CommentRequest;
import com.tms.taskservice.dto.TaskRequest;
import com.tms.taskservice.dto.TaskResponse;
import com.tms.taskservice.model.Comment;
import com.tms.taskservice.utils.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest request);

    TaskResponse updateTask(String id, TaskRequest request);

    TaskResponse assignTask(String taskId, String userId);

    TaskResponse updateStatus(String taskId, TaskStatus status);

    void deleteTask(String id);

    List<TaskResponse> getTasksByProject(String projectId);

    List<TaskResponse> getTasksByUser(String userId);

    Comment addComment(String taskId, CommentRequest commentRequest);

    void deleteComment(String taskId,String commentId);
}