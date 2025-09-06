package dev.task.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.task.models.Task;

public interface TaskRepository {
    void save(Task task);
    void update(Task task);
    void delete(UUID id);
    List<Task> findAll();
    Optional<Task> findById(UUID id);
}
