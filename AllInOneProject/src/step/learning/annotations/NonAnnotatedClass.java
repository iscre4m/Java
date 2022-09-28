package step.learning.annotations;

public class NonAnnotatedClass {
    public int firstField;
    public String secondField;
    public double thirdField;

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