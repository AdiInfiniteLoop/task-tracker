package dev.task.repo;

import java.util.List;
import dev.task.models.Task;

public interface TaskRepository {
    List<Task> findAll();
    void saveAll(List<Task> tasks);
}
