package step.learning.services;

import java.util.Random;

public class RandomToTenProvider {
    private final int number = new Random().nextInt(11);

    public int getNumber() {
        return number;
    }
}