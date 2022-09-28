package step.learning.annotations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationsDemo {
    public void run() {
        Class<?> annotatedClass = AnnotatedClass.class;

        NonAnnotatedClass obj = new NonAnnotatedClass();
        Class<?> nonAnnotatedClass = obj.getClass();

        Class<?> mixedClass;
        try {
            mixedClass = Class.forName(
                    "step.learning.annotations.MixedClass");
        } catch (ClassNotFoundException ex) {
            System.out.printf("Class '%s' not found", ex.getMessage());
            return;
        }

        checkForTypeAnnotation(annotatedClass);
        checkForTypeAnnotation(nonAnnotatedClass);
        checkForTypeAnnotation(mixedClass);
        System.out.println();

        checkForMethodAnnotation(annotatedClass);
        checkForMethodAnnotation(nonAnnotatedClass);
        checkForMethodAnnotation(mixedClass);

        checkForFieldAnnotation(annotatedClass);
        checkForFieldAnnotation(nonAnnotatedClass);
        checkForFieldAnnotation(mixedClass);

        System.out.println("Done");
    }

    private void checkForTypeAnnotation(Class<?> classToCheck) {
        TypeAnnotation annotation = classToCheck
                .getAnnotation(TypeAnnotation.class);

        if (annotation == null) {
            System.out.printf("Class '%s' has no TypeAnnotation%n",
                    classToCheck.getSimpleName());
        } else {
            System.out.printf("Class '%s' has TypeAnnotation%n",
                    classToCheck.getSimpleName());
        }
    }

    private void checkForMethodAnnotation(Class<?> classToCheck) {
        Object obj = null;

        try {
            obj = classToCheck.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                 | InvocationTargetException | NoSuchMethodException ex) {
            System.out.println(ex.getMessage());
        }

        Method[] methods = classToCheck.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MethodAnnotation.class)) {
                System.out.printf(
                        "Method '%15s' in class '%15s' has MethodAnnotation with value '%12s'%n",
                        method.getName(), classToCheck.getSimpleName(),
                        method.getAnnotation(MethodAnnotation.class).value());
            } else {
                System.out.printf("Method '%15s' in class '%15s' has no MethodAnnotation%n",
                        method.getName(), classToCheck.getSimpleName());
            }

            try {
                method.setAccessible(true);
                method.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println();
    }

    private void checkForFieldAnnotation(Class<?> classToCheck) {
        Field[] fields = classToCheck.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(FieldAnnotation.class)) {
                System.out.printf("Field '%12s' in class '%s' has FieldAnnotation%n",
                        field.getName(), classToCheck.getSimpleName());
                System.out.printf("Version: %s%n",
                        field.getAnnotation(FieldAnnotation.class).version());
                System.out.printf("Priority: %d%n",
                        field.getAnnotation(FieldAnnotation.class).priority());
            } else {
                System.out.printf("Field '%15s' in class '%s' has no FieldAnnotation%n",
                        field.getName(), classToCheck.getSimpleName());
            }
        }
        System.out.println();
    }
}