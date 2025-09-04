package dev.task.models;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Task {
    public UUID id;
    public String description;
    public Status status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public Date updatedAt;


    public Task(String description) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    
}
