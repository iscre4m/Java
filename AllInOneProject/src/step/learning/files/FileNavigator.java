package step.learning.files;

import java.io.File;
import java.util.Scanner;

public class FileNavigator {
    private Scanner scanner;

    public FileNavigator() {
        scanner = new Scanner(System.in);
    }

    public void run() {
        String[] list = new File("./").list();

        if(list != null) {
            System.out.printf("'%s' content:%n", "./");
            for (String file : list) {
                System.out.printf(" > %20s%n", file);
            }
        }
    }
}