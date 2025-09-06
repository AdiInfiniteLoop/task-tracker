package dev.task.repo;

import java.util.List;

import dev.task.models.Task;

public interface TaskRepo {
    List<Task> findAll();
    void saveAll(List<Task> tasks);
    
} 