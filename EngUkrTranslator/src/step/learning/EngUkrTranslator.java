package step.learning;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class EngUkrTranslator {
    private final Scanner scanner;
    private final Map<String, String> engToUkr;

    public EngUkrTranslator() {
        scanner = new Scanner(System.in);
        engToUkr = new HashMap<>();
        engToUkr.put("ladder", "дробина");
        engToUkr.put("umbrella", "парасоля");
        engToUkr.put("hit", "влучити");
        engToUkr.put("correspondence", "збіжність");
    }

    public void run() {
        while (true) {
            printStartMessage();

            int choice;
            while (true) {
                try {
                    choice = scanner.nextInt();
                    break;
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input");
                } finally {
                    //для сброса каретки
                    scanner.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    showAll();
                    break;
                case 2:
                    translateToUkr();
                    break;
                case 3:
                    translateToEng();
                    break;
                case 4:
                    addNew();
                    break;
                default:
                    return;
            }
            System.out.println();
        }
    }

    private void printStartMessage() {
        String message = "Англо-Украинский словарь\n";
        message += "    1. Показать все\n";
        message += "    2. Перевод англ. слова\n";
        message += "    3. Перевод укр. слова\n";
        message += "    4. Добавить слово\n";
        message += "    0. Выход\n";
        message += "Введите выбор: ";

        System.out.print(message);
    }

    private void showAll() {
        for (String key : engToUkr.keySet()) {
            System.out.printf("%s - %s%n", key, engToUkr.get(key));
        }
    }

    private void translateToUkr() {
        System.out.print("Enter the word: ");
        String word = scanner.nextLine();
        String translation = engToUkr.get(word);

        if (translation == null) {
            System.out.printf("Word '%s' was not found", word);
            return;
        }

        System.out.printf("%s - %s%n", word, translation);
    }

    private void translateToEng() {
        System.out.print("Enter the word: ");
        String word = scanner.nextLine();
        String translation = null;

        for (String key : engToUkr.keySet()) {
            if (word.equals(engToUkr.get(key))) {
                translation = key;
            }
        }

        if (translation == null) {
            System.out.printf("Word '%s' was not found", word);
            return;
        }

        System.out.printf("%s - %s%n", word, translation);
    }

    private void addNew() {
        System.out.print("Enter the word: ");
        String word = scanner.nextLine();
        System.out.print("Enter the translation: ");
        String translation = scanner.nextLine();

        engToUkr.put(word, translation);
        System.out.println("Done.");
    }
}