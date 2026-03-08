package com.tms.taskservice.repository;

import com.tms.taskservice.model.Task;
import com.tms.taskservice.utils.TaskStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByProjectId(String projectId);

    List<Task> findByAssignedTo(String userId);

    List<Task> findByStatus(TaskStatus status);
}

