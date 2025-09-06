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
    }

    public void update(UUID id, String desc) {
        var task = service.updateTask(id, desc);
        if (task == null) System.out.println("Task not found.");
        else System.out.println("Task updated: " + task);
    }

    public void delete(UUID id) {
        service.deleteTask(id);
        System.out.println("Task deleted");
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
