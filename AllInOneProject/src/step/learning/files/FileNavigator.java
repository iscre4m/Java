package step.learning.files;

import java.io.File;
import java.util.Scanner;

public class FileNavigator {
    private Scanner scanner;
    private String currentFolder;

    public FileNavigator() {
        scanner = new Scanner(System.in);
        currentFolder = "./";
    }

    public void run() {
        String[] list = new File(currentFolder).list();

        if (list != null) {
            System.out.printf("'%s' content:%n", currentFolder);
            for (String file : list) {
                System.out.printf(" > %20s%n", file);
            }
        }
        String command;
        String[] parts;
        while (true) {
            System.out.println("Input command:");
            command = scanner.nextLine();
            parts = command.split(" ");

            switch (parts[0]) {
                case "exit" -> {
                    return;
                }
                case "cd" -> {
                    if (parts.length == 1) {
                        System.out.println("No argument provided");
                        break;
                    }
                    cd(parts[1]);
                }
                case "cat" -> {
                    if (parts.length == 1) {
                        System.out.println("No argument provided");
                        break;
                    }
                    cat(parts[1]);
                }
                default -> System.out.printf("Unknown command '%s'%n", parts[0]);
            }
            System.out.println();
        }
    }

    private void cd(String folder) {
        System.out.printf("cd on %s%n", folder);
    }

    private void cat(String fileName) {
        System.out.printf("cat on %s%n", fileName);
    }
}