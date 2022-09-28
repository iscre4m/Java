package step.learning;

import step.learning.annotations.DemoClass;
import step.learning.annotations.EntryPoint;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String packageName = Main.class.getPackage().getName();
        List<String> classNames = getClassNames(packageName);

        if (classNames == null) {
            System.out.println("Scanning error. Program terminated");
            return;
        }

        List<Class<?>> demoClasses = new ArrayList<>();
        Class<?> classToAdd;
        for (String className : classNames) {
            try {
                classToAdd = Class.forName(className);
            } catch (Exception ignored) {
                continue;
            }

            if (classToAdd.isAnnotationPresent(DemoClass.class)) {
                demoClasses.add(classToAdd);
            }
        }

        int i = 1;
        for (Class<?> demoClass : demoClasses) {
            System.out.printf("%d. %s%n", i++, demoClass.getSimpleName());
        }
        System.out.println("0. Exit");
        System.out.println("Make a choice:");
        Scanner scanner = new Scanner(System.in);
        int userInput = -1;
        try {
            userInput = scanner.nextInt();
        } catch (InputMismatchException ignored) {
            System.out.println("Incorrect input");
            return;
        }

        Class<?> classToExecute = demoClasses.get(userInput - 1);
        Method[] methods = classToExecute.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(EntryPoint.class)) {
                try {
                    method.invoke(classToExecute
                            .getDeclaredConstructor()
                            .newInstance());
                } catch (Exception ex) {
                    System.out.printf("Entry point error %s%n",
                            ex.getMessage());
                }
            }
        }
    }

    private static List<String> getClassNames(String packageName) {
        ClassLoader classLoader = Thread
                .currentThread()
                .getContextClassLoader();
        URL resourceUrl = classLoader
                .getResource(packageName
                        .replace('.', '/'));

        if (resourceUrl == null) {
            System.out.println("Unable to locate resource");
            return null;
        }

        File packageBase = new File(resourceUrl.getPath());
        File[] list = packageBase.listFiles();

        if (list == null) {
            System.out.printf("Unable to scan resource '%s'%n",
                    packageBase.getName());
            return null;
        }

        List<String> classNames = new ArrayList<>();

        for (File file : list) {
            if (file.isFile()) {
                String fileName = file.getName();
                if (fileName.endsWith(".class")) {
                    classNames.add(String.format("%s.%s",
                            packageName,
                            fileName.substring(0,
                                    fileName.lastIndexOf('.'))));
                }
            } else {
                classNames.addAll(getClassNames(String.format("%s.%s", packageName, file.getName())));
            }
        }

        return classNames;
    }
}