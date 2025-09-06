package dev.task.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dev.task.models.Status;
import dev.task.models.Task;

public class TaskController {
    private final File file = new File("tasks.json");
    private List<Task> tasks = new ArrayList<>(); 
    private ObjectMapper mapper;

    public TaskController() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty-print
        tasks = loadTasks(); // initialize tasks from JSON
    }

    public void addTask(String description) throws IOException {
        Task task = new Task(description);
        tasks.add(task);
        saveTasks();
    }

    public Task updateTask(UUID id, String description) {
        for(Task t: tasks) {
            if(t.id.equals(id)) {
                t.description = description;
                saveTasks();
                return t;
            }
        }
        return null;
    }

    public void deleteTask(UUID id) {
        boolean removed = tasks.removeIf(t->t.id.equals(id));
        if(removed) {
            saveTasks();
        }
        return;
    }

    public void markInProgress(UUID id) {
        for(Task t: tasks) {
            if(t.id.equals(id)) {
                t.status = Status.PROGRESS;
            }
        }
        saveTasks();
    }

    public void markDone(UUID id) {
        for(Task t: tasks) {
            if(t.id.equals(id)) {
                t.status = Status.DONE;
            }
        }
        saveTasks();
    }

    public List<Task> listDone() {
        List<Task> li = new ArrayList<>();
        for(Task t: tasks) {
            if(t.status.equals(Status.DONE)) {
                System.out.println(t.description);
                li.add(t);
            }
        }
        return li;
    }

    public List<Task> listInProgress() {
        List<Task> li = new ArrayList<>();
        for(Task t: tasks) {
            if(t.status.equals(Status.PROGRESS)) {
                System.out.println(t.description);
                li.add(t);
            }
        }
        return li;
    }

    public List<Task> listTodo() {
        List<Task> li = new ArrayList<>();
        for(Task t: tasks) {
            if(t.status.equals(Status.TODO)) {
                System.out.println(t.description);
                li.add(t);
            }
        }
        return li;
    }

    public List<Task> listAll() {
        for(Task t: tasks) {
            System.out.println(t.description);
        }
        return tasks;
    }

        // Load tasks from JSON file
    private List<Task> loadTasks() {

        try {
            if (file.exists()) {
                Task[] arr = mapper.readValue(file, Task[].class);
                return new ArrayList<>(List.of(arr));
            }
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void saveTasks() {
        try {
            mapper.writeValue(file, tasks);
        } catch (Exception e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
