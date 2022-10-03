package step.learning.annotations;

import java.util.Random;

@TypeAnnotation
public class AnnotatedClass {
    public AnnotatedClass() {
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

    @FieldAnnotation(version = "1.0", priority = 1)
    public int firstField;

    @FieldAnnotation(version = "1.2", priority = 2)
    protected String secondField;

    @FieldAnnotation(version = "1.2", priority = 3)
    private double thirdField;

    @MethodAnnotation("Recommended")
    public void firstMethod() {
        System.out.println("first method");
    }

    @MethodAnnotation("Default")
    protected int secondMethod() {
        System.out.println("second method");

        return 0;
    }

    @MethodAnnotation("Deprecated")
    private boolean thirdMethod() {
        System.out.println("third method");

        return false;
    }
}