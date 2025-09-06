package dev.task.repo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dev.task.models.Task;

public class JsonTaskRepository implements TaskRepository {
    private final File file = new File("tasks.json");
    private final ObjectMapper mapper;

    public JsonTaskRepository() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public List<Task> findAll() {
        try {
            if (file.exists()) {
                Task[] arr = mapper.readValue(file, Task[].class);
                return new ArrayList<>(List.of(arr));
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void saveAll(List<Task> tasks) {
        try {
            mapper.writeValue(file, tasks);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
