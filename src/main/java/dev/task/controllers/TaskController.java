package dev.task.controllers;

import java.util.UUID;

import dev.task.models.Status;
import dev.task.services.TaskService;

public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    public void add(String desc) {
        if (desc.isEmpty()) {
            System.out.println("Oops! Empty Task!");
            return;
        }
        var task = service.addTask(desc);
        System.out.println("Task added: " + task);
    }

    public void update(UUID id, String desc) {
        var task = service.updateTask(id, desc);
        if (task == null) System.out.println("Task not found.");
        else System.out.println("Task updated: " + task);
    }

    public void delete(UUID id) {
        boolean success = service.deleteTask(id);
        if (success) System.out.println("Task deleted.");
        else System.out.println("Task not found.");
    }

    public void markInProgress(UUID id) {
        service.markStatus(id, Status.PROGRESS);
    }

    public void markDone(UUID id) {
        service.markStatus(id, Status.DONE);
    }

    public void list(String filter) {
        switch (filter.toLowerCase()) {
            case "":
                service.listAll();
                break;
            case "done":
                service.listByStatus(Status.DONE);
                break;
            case "todo":
                service.listByStatus(Status.TODO);
                break;
            case "in-progress":
                service.listByStatus(Status.PROGRESS);
                break;
            default:
                System.out.println("Unknown list filter: " + filter);
        }
    }
}
