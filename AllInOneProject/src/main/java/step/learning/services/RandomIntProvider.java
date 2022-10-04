package step.learning.services;

import com.google.inject.Singleton;

import java.util.Random;

@Singleton
public class RandomIntProvider implements RandomNumberProvider {
    private final int number = new Random().nextInt();

    public int getNumber() {
        return number;
    }
}