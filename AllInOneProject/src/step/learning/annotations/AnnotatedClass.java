package step.learning.annotations;

@TypeAnnotation
public class AnnotatedClass {
    @FieldAnnotation(version="1.0", priority = 1)
    public int firstField;

    @FieldAnnotation(version="1.2", priority = 2)
    public String secondField;

    @FieldAnnotation(version="1.2", priority = 3)
    public double thirdField;

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