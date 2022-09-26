package step.learning.files;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FileNavigator {
    private Scanner scanner;
    private String currentFolder;

    public FileNavigator() {
        scanner = new Scanner(System.in);
        currentFolder = String.format(".%s", File.separator);
    }

    public void run() {
        ls();

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
                case "ls" -> ls();
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

    private void ls() {
        String[] list = new File(currentFolder).list();

        if (list != null) {
            System.out.printf("'%s' content:%n", currentFolder);
            for (String file : list) {
                System.out.printf(" > %20s%n", file);
            }
        }
    }

    private void cd(String path) {
        File file = new File(path);

        if (file.isFile()) {
            System.out.printf("'%s' is a file", path);
            return;
        }

        if(!file.exists()) {
            System.out.printf("'%s' doesn't exist", path);
            return;
        }

        currentFolder = path;
        System.out.printf("Current folder is now %s", currentFolder);
    }

    private void cat(String fileName) {
        File file = new File(fileName);

        if (file.isDirectory()) {
            System.out.printf("'%s' is a folder", fileName);
            return;
        }

        if (!file.exists()) {
            System.out.printf("'%s' doesn't exist", fileName);
            return;
        }

        StringBuilder stringBuilder;

        try (InputStream reader = new FileInputStream(file.getName())) {
            int symbol;
            stringBuilder = new StringBuilder();

            while ((symbol = reader.read()) != -1) {
                stringBuilder.append((char) symbol);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        String content = new String(
                stringBuilder.toString().getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8);
        System.out.println(content);
    }
}