package step.learning;

import com.google.inject.Inject;
import step.learning.annotations.DemoClass;
import step.learning.annotations.EntryPoint;
import step.learning.services.RandomNumberProvider;
import step.learning.services.Service;
import step.learning.services.SymbolService;
import step.learning.services.TimeService;

import javax.inject.Named;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class App {
    @Inject
    private Service service;
    @Inject
    private RandomNumberProvider random;
    @Inject
    private TimeService timeService;

    private final SymbolService symbolService;
    private final String mssqlConStr;
    private final String oracleSqlConStr;

    private static List<Class<?>> demoClasses;
    private static Map<Class<?>, Object> cachedObjects;

    @Inject
    public App(
            @Named("CharProvider") SymbolService symbolService,
            @Named("MSSQL") String mssqlConStr,
            @Named("OracleSQL") String oracleSqlConStr) {
        this.symbolService = symbolService;
        this.mssqlConStr = mssqlConStr;
        this.oracleSqlConStr = oracleSqlConStr;
    }

    public void run() {
        System.out.println(service.getString());
        System.out.println(random.getNumber());
        System.out.println(timeService.getDate());
        System.out.println(timeService.getTime());
        System.out.println(symbolService.getSymbol());
        System.out.format("MSSQL: '%s' OracleSQL: '%s'%n",
                mssqlConStr, oracleSqlConStr);
    }

    public void run2() {
        cachedObjects = new HashMap<>();
        demoClasses = getDemoClasses();
        if (demoClasses == null) {
            System.out.println("Scanning error. Program terminated");
            return;
        }

        int userInput;
        while (true) {
            printMenu();
            userInput = getUserInput();
            if (userInput == 0) {
                return;
            }
            handleUserInput(userInput);
            System.out.println();
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
        List<String> classNamesToAdd;

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
                classNamesToAdd = getClassNames(
                        String.format("%s.%s",
                                packageName, file.getName()));

                if (classNamesToAdd != null) {
                    classNames.addAll(classNamesToAdd);
                }
            }
        }

        return classNames;
    }

    private static List<Class<?>> getDemoClasses() {
        String packageName = Main.class.getPackage().getName();
        List<String> classNames = getClassNames(packageName);

        if (classNames == null) {
            return null;
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

        return demoClasses;
    }

    private static void printMenu() {
        int i = 1;

        //Значения приоритетов (выше - приоритетнее):
        //AnnotationsDemo - 0
        //FilesDemo - 0
        //FileNavigator - 3
        //SerializationDemo - 1
        //Library - 2
        demoClasses.sort((first, second) -> {
            int fPriority = first
                    .getAnnotation(DemoClass.class)
                    .priority();
            int sPriority = second
                    .getAnnotation(DemoClass.class)
                    .priority();

            return Integer.compare(sPriority, fPriority);
        });

        for (Class<?> demoClass : demoClasses) {
            System.out.printf("%d. %s%n", i++, demoClass.getSimpleName());
        }
        System.out.println("0. Exit");
        System.out.println("Make a choice:");
    }

    private static int getUserInput() {
        Scanner scanner = new Scanner(System.in);
        int userInput;

        while (true) {
            try {
                userInput = scanner.nextInt();

                if (userInput < 0 || userInput > demoClasses.size()) {
                    System.out.printf("Option '%d' is invalid%n", userInput);
                    continue;
                }

                return userInput;
            } catch (InputMismatchException ignored) {
                System.out.println("Incorrect input type");
                scanner.nextLine();
            }
        }
    }

    private static void handleUserInput(int input) {
        Class<?> classToExecute = demoClasses.get(input - 1);
        Method[] methods = classToExecute.getDeclaredMethods();
        Object objectToCache;

        for (Method method : methods) {
            if (method.isAnnotationPresent(EntryPoint.class)) {
                try {
                    if (cachedObjects.containsKey(classToExecute)) {
                        method.invoke(cachedObjects.get(classToExecute));
                        continue;
                    }
                    objectToCache = classToExecute
                            .getDeclaredConstructor()
                            .newInstance();
                    cachedObjects.put(classToExecute, objectToCache);
                    method.invoke(objectToCache);
                } catch (Exception ex) {
                    System.out.printf("Entry point error: %s%n",
                            ex.getMessage());
                }
            }
        }
    }
}
// []‾\  /‾()
// []--<>--()
// []_/  \_()