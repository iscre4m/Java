package step.learning.annotations;

public class MixedClass {
    @FieldAnnotation(priority = 1)
    public int firstField;

    @FieldAnnotation(version = "1.2")
    public String secondField;

    @FieldAnnotation()
    public double thirdField;

    public void firstMethod() {
        System.out.println("first method");
    }

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