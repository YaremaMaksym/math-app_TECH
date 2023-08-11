package org.xsakon;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean keepLooping = true;

        while (keepLooping) {
            try {
                displayMenu();
                System.out.print("Choice: ");
                String choice = scanner.nextLine();
                switch (Integer.parseInt(choice)) {
                    case 1 -> enterExpressions();
                    case 2 -> keepLooping = false;
                    default -> System.out.println(choice + " not a valid option");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + ". Please enter a number");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("""
                \n
                Menu:
                1️⃣ - Enter expression
                2️⃣ - Exit
                """);
    }

    private static String enterExpressions() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter your expression: ");
        String expression = scanner.nextLine();

        ExpressionValidator.checkCorrectness(expression);

        return expression;
    }

}

