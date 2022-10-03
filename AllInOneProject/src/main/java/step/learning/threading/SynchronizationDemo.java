package step.learning.threading;

import step.learning.annotations.DemoClass;
import step.learning.annotations.EntryPoint;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@DemoClass
public class SynchronizationDemo {
    @EntryPoint
    public void demo() {
        System.out.println("Synchronization demo");
        /*
        sum = 100;
        threads = 12;
        for (int i = 0; i < 12; i++) {
            new Thread(plusTenPercentFinal).start();
        }

        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.submit(() -> System.out.println("Pool works"));

        Future<String> task = pool.submit(() -> "callable");

        try {
            String result = task.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println(ex.getMessage());
        }

        pool.shutdown();
         */

        ExecutorService pool = Executors.newFixedThreadPool(12);
        sum = 100;
        threads = 12;

        for (int i = 0; i < 12; i++) {
            pool.submit(plusTenPercentFinal);
        }

        pool.shutdown();
    }

    private int threads;
    private double sum;

    private final Runnable plusTenPercent = () -> {
        double tmp = sum;
        tmp *= 1.1;
        System.out.printf("Current sum: %.2f%n", tmp);
        sum = tmp;
    };

    private final Object sumLocker = new Object();
    private final Runnable plusTenPercentSynchronized = () -> {
        synchronized (sumLocker) {
            double tmp = sum;
            tmp *= 1.1;
            System.out.printf("Current sum: %.2f%n", tmp);
            sum = tmp;
        }
    };

    private final Runnable plusTenPercentSynchronizedCorrectly = () -> {
        double tmp;
        synchronized (sumLocker) {
            tmp = sum *= 1.1;
        }
        System.out.printf("Current sum: %.2f%n", tmp);
    };

    private final Runnable plusTenPercentFinal = () -> {
        double tmp;
        boolean isLast;
        synchronized (sumLocker) {
            tmp = sum *= 1.1;
            isLast = --threads == 0;
        }

        System.out.printf("Current sum: %.2f %s%n", tmp, isLast ? "final" : "");
    };
}