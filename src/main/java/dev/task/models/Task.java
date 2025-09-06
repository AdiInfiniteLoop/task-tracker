package dev.task.models;

import java.util.UUID;

public class Task {
    public UUID id;
    public String description;
    public Status status;

    public Task() {} // required for Jackson

    public Task(String description) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.status = Status.TODO;
    }

    @Override
    public String toString() {
        return id + " | " + status + " | " + description;
    }
}
