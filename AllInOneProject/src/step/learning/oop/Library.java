package step.learning.oop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.*;

public class Library {
    private List<Literature> funds;
    private final String PATH = "./src/step/learning/oop/funds.ser";

    public Library() {
        funds = new ArrayList<>();
    }

    public void add(Literature literature) {
        funds.add(literature);
    }

    public void printFunds() {
        for (Literature literature : funds) {
            if (literature instanceof Printable) {
                ((Printable) literature).print();
                continue;
            }

            System.out.printf("Unprintable: '%s'%n", literature.getTitle());
        }
    }

    public void printPeriodic() {
        for (Literature literature : funds) {
            if (!(literature instanceof Printable)) {
                System.out.printf("Unprintable: '%s'%n", literature.getTitle());
                continue;
            }
            if (!(literature instanceof Periodic)) {
                System.out.printf("Not periodic: '%s'%n", literature.getTitle());
                continue;
            }

            ((Printable) literature).print();
        }
    }

    public void printNonPeriodic() {
        for (Literature literature : funds) {
            if (!(literature instanceof Printable)) {
                System.out.printf("Unprintable: '%s'%n", literature.getTitle());
                continue;
            }
            if ((literature instanceof Periodic)) {
                System.out.printf("Periodic: '%s'%n", literature.getTitle());
                continue;
            }

            ((Printable) literature).print();
        }
    }

    @SuppressWarnings("unchecked")
    public void run() {
        // прошла ли десериализация успешно
        boolean deserialized = true;

        try (FileInputStream file = new FileInputStream(PATH)) {
            ObjectInputStream ois = new ObjectInputStream(file);
            funds = (List<Literature>) ois.readObject();
        } catch (FileNotFoundException ex) {
            System.out.println("Deserialization skipped, file not found");
            deserialized = false;
        } catch (Exception ex) {
            System.out.printf("Deserialization error: %s", ex.getMessage());

            return;
        }

        if (deserialized) {
            System.out.printf("%2d objects deserialized%n%n", funds.size());
        }

        int choice;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose what to do:");
            System.out.println("1. All funds");
            System.out.println("2. Periodic funds");
            System.out.println("3. Non-periodic funds");
            System.out.println("0. Exit");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printFunds();
                    break;
                case 2:
                    printPeriodic();
                    break;
                case 3:
                    printNonPeriodic();
                    break;
                default:
                    return;
            }
            System.out.println();
        }
    }
}