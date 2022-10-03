package step.learning;

import java.util.*;

public class ComplexDataTypes {
    private final Random random;

    public ComplexDataTypes() {
        random = new Random();
    }

    private void arraysDemo() {
        for (int item : new int[5]) {
            System.out.print(item + " ");
        }
        System.out.println();

        int[] second = new int[]{1, 1, 1, 1, 1};

        for (int item : second) {
            System.out.printf("%d ", item);
        }
        System.out.println();

        int[] third = {2, 2, 2, 2, 2};
        for (int item : third) {
            System.out.print(item + " ");
        }
        System.out.println("\n");

        int[][] fourth = new int[3][4];
        randomizeArray(fourth);
        printArray(fourth);

        int[][] fifth = {
                {1, 2, 3},
                {4, 5, 6, 7},
                {8, 9}
        };

        printArray(fifth);
    }

    private void printArray(int[][] array) {
        for (int[] subArray : array) {
            for (int item : subArray) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void randomizeArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = random.nextInt(42);
            }
        }
    }

    private void collectionsDemo() {
        List<Integer> array = new ArrayList<>();
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);
        array.add(5);

        for (int item : array) {
            System.out.print(item + " ");
        }
        System.out.println("\n");

        array.set(1, 6);
        array.remove(4);
        for (int item : array) {
            System.out.print(item + " ");
        }
        System.out.println("\n");

        Map<String, String> fruits = new HashMap<>();
        fruits.put("Coconut", "Кокос");
        fruits.put("Pineapple", "Ананас");
        fruits.put("Pear", "Груша");

        for (String fruit : fruits.keySet()) {
            System.out.printf("%s - %s%n", fruit, fruits.get(fruit));
        }

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.printf("%s - %s%n", input, fruits.get(input));
    }

    public void run() {
        //arraysDemo();
        collectionsDemo();
    }
}