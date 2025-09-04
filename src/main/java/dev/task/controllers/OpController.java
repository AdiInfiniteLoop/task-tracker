package dev.task.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dev.task.models.Task;

public class OpController {
    private final File file = new File("tasks.json");
    private List<Task> tasks = new ArrayList<>(); 
    private ObjectMapper mapper;

        public OpController() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty-print
        tasks = loadTasks(); // initialize tasks from JSON
    }

    public void addTask(String description) throws IOException {
        //Push to json
        //Returns success 
        Task task = new Task(description);
        tasks.add(task);
    }

    // public Task updateTask(UUID id, String description) {
    //     //updates task
    //     //returns new task
    // }
    // /* Update task can be polymorphed */
    // public boolean deleteTask(UUID id) {

    // }

    // public boolean markInProgress(UUID id) {

    // }

    // public boolean markDone(UUID id) {

    // }

    // public List<Task> listDone() {
        
    // }

    // public List<Task> listInProgress() {

    // }

    // public List<Task> listTodo() {
        
    // }

    public List<Task> listAll() {
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
