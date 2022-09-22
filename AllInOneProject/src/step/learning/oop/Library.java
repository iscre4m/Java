package step.learning.oop;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Literature> funds;

    public Library() {
        funds = new ArrayList<>();
    }

    public Library add(Literature literature) {
        funds.add(literature);

        return this;
    }

    public Library printFunds() {
        for (Literature literature : funds) {
            literature.print();
        }

        return this;
    }

    public void run() {
        add(new Book()
                .setTitle("First Day")
                .setAuthor("John Bot"))
                .add(new Journal()
                        .setNumber(1)
                        .setTitle("Hot Wings in your area"))
                .printFunds()
                .add(new Book()
                        .setTitle("Grokking Algorithms")
                        .setAuthor("Aditya Bhargava"))
                .printFunds();
    }
}