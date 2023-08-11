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
        boolean isCorrect = true;

        String expression = scanner.nextLine();

        int wrongBracketIndex = checkBracketCorrectness(expression);
        if (wrongBracketIndex != -1) {
            highlightError(expression, "Wrong bracket placement", wrongBracketIndex);
            isCorrect = false;
        }

        if (isCorrect) {
            System.out.println("Expression is correct");
        }

        return expression;
    }

    private static int checkBracketCorrectness(String expression) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(') {
                stack.push(i);
            } else if (ch == ')') {
                if (stack.isEmpty()) {
                    return i;  // Redundant closing bracket
                }
                stack.pop();
            }
        }

        if (!stack.isEmpty()) {
            return stack.peek();  // Redundant opening bracket
        }

        return -1; // All brackets correct
    }

    public static void highlightError(String expression, String message, int errorIndex) {
        System.out.println("Error: " + message);
        System.out.println(expression);
        for (int i = 0; i < errorIndex; i++) {
            System.out.print(" ");
        }
        System.out.println("^ Here");
    }


}

