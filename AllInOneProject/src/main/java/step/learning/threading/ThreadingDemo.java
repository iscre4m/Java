package step.learning.threading;

import step.learning.ConsoleColors;
import step.learning.annotations.DemoClass;
import step.learning.annotations.EntryPoint;

import java.util.Scanner;

@DemoClass(priority = 1)
public class ThreadingDemo {
    private String shared;
    private int threadNumber;

    @EntryPoint
    public void run() {
        //region Classwork
        System.out.printf("%sThreading Demo%s%n",
                ConsoleColors.YELLOW_BOLD_BRIGHT, ConsoleColors.RESET);

        new PrintThread().start();

        new ArgThread("Soloma").start();

        ArgThread argThread = new ArgThread();
        argThread.setArg("Derevo");
        argThread.start();

        new Thread(() ->
                System.out.println("Running anonymous thread")).start();

        Runnable runnable = () ->
                System.out.println("Running runnable thread");
        new Thread(runnable).start();

        shared = "first";
        new Thread(() ->
                System.out.printf(
                        "Running anonymous thread with shared value '%s'%n",
                        shared)).start();

        shared = "second";
        new Thread(() ->
                System.out.printf(
                        "Running another anonymous thread with shared value '%s'%n",
                        shared)).start();

        shared = "first";
        //endregion

        //region Homework
        System.out.print("Input number of threads: ");
        int userInput;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                userInput = scanner.nextInt();

                if (userInput > 0) {
                    break;
                }

                System.out.println("Invalid input");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                scanner.nextLine();
            }
        }

        do {
            int localThreadNumber = ++threadNumber;
            new Thread(
                    () -> System.out.printf("Running thread #%d%n",
                            localThreadNumber)
            ).start();
        } while (threadNumber < userInput);
        //endregion
    }

    static class PrintThread extends Thread {
        @Override
        public void run() {
            System.out.println("Running print thread");
        }
    }

    static class ArgThread extends Thread {
        private String arg;

        public ArgThread() {
            this.arg = "default";
        }

        public ArgThread(String arg) {
            this.arg = arg;
        }

        public void setArg(String arg) {
            this.arg = arg;
        }

        @Override
        public void run() {
            System.out.printf("Running arg thread with value '%s'%n", arg);
        }
    }
}