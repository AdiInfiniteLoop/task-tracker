package dev.task;

import dev.task.cli.CLI;
import dev.task.controllers.TaskController;
import dev.task.repo.MongoTaskRepository;
import dev.task.services.TaskService;


public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to Task Tracker CLI");
        MongoTaskRepository repo = new MongoTaskRepository("mongodb://localhost:27017", "taskdb");

        // wire dependencies
        TaskService service = new TaskService(repo);
        TaskController controller = new TaskController(service);

        CLI cli = new CLI(controller);
        cli.start();
    }
}
