package step.learning.annotations;

import java.util.Random;

public class NonAnnotatedClass {
    public NonAnnotatedClass() {
        Random random = new Random();

        firstField = random.nextInt(100);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(
                    (char) random.nextInt(97, 123));
        }
        secondField = stringBuilder.toString();
        thirdField = random.nextDouble(20);
    }

    public int firstField;
    protected String secondField;
    private double thirdField;

    public void firstMethod() {
        System.out.println("first method");
    }

    protected int secondMethod() {
        System.out.println("second method");

        return 0;
    }

    private boolean thirdMethod() {
        System.out.println("third method");

        return false;
    }
}