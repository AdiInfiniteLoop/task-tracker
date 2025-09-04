package dev.task;

import java.util.Scanner;
import dev.task.controllers.OpController;

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to Task Tracker CLI");
        OpController op = new OpController();

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                String input = sc.nextLine().trim();

                if (input.isEmpty()) continue;

                String[] parts = input.split("\\s+", 2);
                String cmd = parts[0];

                if (!cmd.equals("cli")) {
                    System.out.println("Incorrect Input. Do you mean 'cli'?");
                    continue;
                }

                if (parts.length < 2) {
                    System.out.println("No command provided after 'cli'.");
                    continue;
                }

                String[] actionParts = parts[1].split("\\s+", 2);
                String action = actionParts[0];
                String taskName = (actionParts.length > 1) ? actionParts[1].trim() : "";

                switch (action.toLowerCase()) {
                    case "add":
                        if (taskName.isEmpty()) {
                            System.out.println("Oops! Empty Task!");
                        } else {
                            op.addTask(taskName);
                            System.out.println("Adding task: " + taskName);
                        }
                        break;

                    case "update":
                        System.out.println("Update task: " + taskName);
                        break;

                    case "delete":
                        System.out.println("Delete task: " + taskName);
                        break;

                    case "list":
                        switch (taskName.toLowerCase()) {
                            case "":
                                System.out.println("Listing all tasks...");
                                break;
                            case "done":
                                System.out.println("Listing done tasks...");
                                // op.listDoneTasks();
                                break;
                            case "todo":
                                System.out.println("Listing todo tasks...");
                                // op.listTodoTasks();
                                break;
                            case "in-progress":
                                System.out.println("Listing in-progress tasks...");
                                // op.listInProgressTasks();
                                break;
                            default:
                                System.out.println("Unknown list filter: " + taskName);
                        }
                        break;

                    default:
                        System.out.println("Unknown action: " + action);
                }
                op.saveTasks();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
