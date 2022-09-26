package step.learning.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FilesDemo {
    public void run() {
        //fsDemo();
        ioDemo();
    }

    // region FS

    /**
     * File system demo
     */
    private void fsDemo() {
        File file = new File(String.format(".%scon", File.separator));

        if (file.isDirectory()) {
            System.out.printf("'%s' - is a directory%n", file.getName());
            String[] list = file.list();

            if (list != null) {
                for (String fileName : list) {
                    System.out.printf(" > %20s%n", fileName);
                }
            }

            System.out.print("Delete dir? (y/...) ");
            try {
                int userInput = System.in.read();
                if (userInput == 'y') {
                    boolean deleted = file.delete();
                    if (deleted) {
                        System.out.println("Deleted");
                        return;
                    }

                    System.out.println("Deletion error");
                }
            } catch (Exception ex) {
                System.out.printf("System error: %s%n", ex.getMessage());
            }
        } else if (file.isFile()) {
            System.out.printf("'%s' - is a file%n", file.getName());

            if (file.canRead()) {
                System.out.println("Readable");
            } else {
                System.out.println("Un-readable");
            }

            if (file.canWrite()) {
                System.out.println("Writable");
            } else {
                System.out.println("Un-writable");
            }

            if (file.canExecute()) {
                System.out.println("Executable");
            } else {
                System.out.println("Un-executable");
            }
        } else {
            System.out.printf("'%s' - doesn't exist%n", file.getName());
            System.out.println("Create directory? (y/...) ");

            try {
                int userInput = System.in.read();

                if (userInput == 'y') {
                    if (file.mkdir()) {
                        System.out.println("Created successfully");
                        return;
                    }

                    System.out.println("Creation error");
                }
            } catch (IOException ex) {
                System.out.printf("System error: %s%n", ex.getMessage());
            }
        }
    }
    // endregion

    // region IO

    /**
     * Input/output with files
     */
    private void ioDemo() {
        String content;
        StringBuilder stringBuilder;

        try (InputStream reader = new FileInputStream("./src/step/learning/files/text.txt")) {
            int symbol;
            stringBuilder = new StringBuilder();

            while ((symbol = reader.read()) != -1) {
                stringBuilder.append((char) symbol);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        content = new String(
                stringBuilder.toString().getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8);
        System.out.println(content);


    }
    // endregion
}