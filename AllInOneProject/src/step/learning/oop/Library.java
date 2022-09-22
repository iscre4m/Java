package step.learning.oop;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private final List<Literature> funds;

    public Library() {
        funds = new ArrayList<>();
    }

    public Library add(Literature literature) {
        funds.add(literature);

        return this;
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

    public void printPeriodicFunds() {
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

    public void run() {
        add(new Book()
                .setTitle("First Day")
                .setAuthor("John Bot"))
                .add(new Journal()
                        .setNumber(1)
                        .setTitle("Hot Wings in your area"))
                .add(new Book()
                        .setTitle("Grokking Algorithms")
                        .setAuthor("Aditya Bhargava"));
        try {
            add(new Newspaper()
                    .setDate("2022-09-30")
                    .setTitle("New Dawn"));
        } catch (ParseException e) {
            System.out.printf("Newspaper creation failed: %s", e.getMessage());

            return;
        }
        add(new Hologram()
                .setTitle("Pectoral"));

        //printFunds();
        printPeriodicFunds();
    }
}