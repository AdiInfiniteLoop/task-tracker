package dev.task;

import java.util.Scanner;
import java.util.UUID;

import dev.task.controllers.TaskController;

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to Task Tracker CLI");
        TaskController op = new TaskController();

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
                            op.addTask(taskName);
                            System.out.println("Adding task: " + taskName);
                        break;

                    case "update":
                            String[] updateParts = taskName.split("\\s+", 2);                          
                            if (updateParts.length < 2) {
                                System.out.println("You must provide both ID and new description!");
                            } else {
                                String idStr = updateParts[0];      
                                String newDesc = updateParts[1];    
                                op.updateTask(UUID.fromString(idStr), newDesc);
                            }
                        break;

                    case "delete":
                        op.deleteTask(UUID.fromString(taskName));
                        break;
                    
                    case "mark-in-progress":
                        if(taskName.isEmpty()) {
                            System.out.println("Please provide a task to mark as done");
                            break;
                        }
                        System.out.println("Marking in progress");
                        op.markInProgress(UUID.fromString(taskName));
                        break;

                    case "mark-done":
                        if(taskName.isEmpty()) {
                            System.out.println("Please provide a task to mark as done");
                            break;
                        }
                        op.markDone(UUID.fromString(taskName));
                        break;
                            
                    case "list":
                        switch (taskName.toLowerCase()) {
                            case "":
                                op.listAll();
                                break;
                            case "done":
                                op.listDone();
                                break;
                            case "todo":
                                op.listTodo();
                                break;
                            case "in-progress":
                                op.listInProgress();
                                break;
                            default:
                                System.out.println("Unknown list filter: " + taskName);
                        }
                        break;

                    default:
                        System.out.println("Unknown action: " + action);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
