package step.learning;

import java.util.Random;

public class RandomNumberProvider {
    private final int number = new Random().nextInt();

    public int getNumber() {
        return number;
    }
}