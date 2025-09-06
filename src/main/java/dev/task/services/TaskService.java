package dev.task.services;

import java.util.List;
import java.util.UUID;

import dev.task.models.Status;
import dev.task.models.Task;
import dev.task.repo.TaskRepository;

public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    // Add a new task
    public void addTask(String desc) {
        Task t = new Task(desc);
        repo.save(t); 
        System.out.println("Task added: " + t.description + " (ID: " + t.id + ")");
    }

    // Update an existing task by ID
    public Task updateTask(UUID id, String description) {
        return repo.findById(id).map(task -> {
            task.description = description;
            repo.update(task);
            System.out.println("Task updated: " + task.description + " (ID: " + task.id + ")");
            return task;
        }).orElseGet(() -> {
            System.out.println("Task not found for ID: " + id);
            return null;
        });
    }

    // Delete a task by ID
    public void deleteTask(UUID id) {
        if (repo.findById(id).isPresent()) {
            repo.delete(id);
            System.out.println("Task deleted with ID: " + id);
        } else {
            System.out.println("Task not found for ID: " + id);
        }
    }

    // Mark a task with a specific status
    public void markStatus(UUID id, Status status) {
        repo.findById(id).ifPresentOrElse(task -> {
            task.status = status;
            repo.update(task);
            System.out.println("Task marked as " + status + " (ID: " + task.id + ")");
        }, () -> System.out.println("Task not found for ID: " + id));
    }

    // List all tasks
    public List<Task> listAll() {
        List<Task> tasks = repo.findAll();
        tasks.forEach(task -> System.out.println(task));
        return tasks;
    }

    // List tasks filtered by status
    public void listByStatus(Status status) {
        repo.findAll().stream()
            .filter(task -> task.status.equals(status))
            .forEach(System.out::println);
    }
}
