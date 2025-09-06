package dev.task.cli;

import java.util.Scanner;
import java.util.UUID;

import dev.task.controllers.TaskController;

public class CLI {
    private final TaskController controller;

    public CLI(TaskController controller) {
        this.controller = controller;
    }

    public void start() {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                String input = sc.nextLine().trim();
                if (input.isEmpty()) continue;

                String[] parts = input.split("\\s+", 2);
                if (!parts[0].equals("cli")) {
                    System.out.println("Incorrect Input. Do you mean 'cli'?");
                    continue;
                }
                if (parts.length < 2) {
                    System.out.println("No command provided after 'cli'.");
                    continue;
                }

                String[] actionParts = parts[1].split("\\s+", 2);
                String action = actionParts[0].toLowerCase();
                String args = (actionParts.length > 1) ? actionParts[1].trim() : "";

                switch (action) {
                    case "add":
                        controller.add(args);
                        break;
                    case "update":
                        String[] updateParts = args.split("\\s+", 2);
                        if (updateParts.length < 2) {
                            System.out.println("Usage: cli update <uuid> <new description>");
                        } else {
                            controller.update(UUID.fromString(updateParts[0]), updateParts[1]);
                        }
                        break;
                    case "delete":
                        controller.delete(UUID.fromString(args));
                        break;
                    case "mark-in-progress":
                        controller.markInProgress(UUID.fromString(args));
                        break;
                    case "mark-done":
                        controller.markDone(UUID.fromString(args));
                        break;
                    case "list":
                        controller.list(args);
                        break;
                    default:
                        System.out.println("Unknown action: " + action);
                }
            }
        }
    }
}
