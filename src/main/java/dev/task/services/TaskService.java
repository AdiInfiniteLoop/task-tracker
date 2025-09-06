package dev.task.services;

import java.util.List;
import java.util.UUID;

import dev.task.models.Status;
import dev.task.models.Task;
import dev.task.repo.TaskRepository;

public class TaskService {
    private final TaskRepository repo;
    private final List<Task> tasks;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
        this.tasks = repo.findAll();
    }

    public Task addTask(String description) {
        Task task = new Task(description);
        tasks.add(task);
        repo.saveAll(tasks);
        return task;
    }

    public Task updateTask(UUID id, String description) {
        for (Task t : tasks) {
            if (t.id.equals(id)) {
                t.description = description;
                repo.saveAll(tasks);
                return t;
            }
        }
        return null;
    }

    public boolean deleteTask(UUID id) {
        boolean removed = tasks.removeIf(t -> t.id.equals(id));
        if (removed) repo.saveAll(tasks);
        return removed;
    }

    public void markStatus(UUID id, Status status) {
        for (Task t : tasks) {
            if (t.id.equals(id)) {
                t.status = status;
                repo.saveAll(tasks);
                System.out.println("Task marked as " + status);
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void listAll() {
        tasks.forEach(System.out::println);
    }

    public void listByStatus(Status status) {
        tasks.stream()
             .filter(t -> t.status.equals(status))
             .forEach(System.out::println);
    }
}
