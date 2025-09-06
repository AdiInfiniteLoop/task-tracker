package dev.task.models;

import java.util.Date;
import java.util.UUID;

public class Task {
    public UUID id;
    public String description;
    public Status status;
    public Date createdAt;

    public Task() {} // required for Jackson

    public Task(String description) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = new Date();
    }

    @Override
    public String toString() {
        return id + " | " + status + " | " + description;
    }
}
