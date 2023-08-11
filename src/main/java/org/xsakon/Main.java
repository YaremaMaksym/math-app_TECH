package org.xsakon;

import java.util.Scanner;

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
        int index = -1;
        int numOpenedBrackets = 0;
        int numClosedBrackets = 0;

        for (Character ch : expression.toCharArray()) {
            if (ch.toString().equals("(")) {
                numOpenedBrackets++;
            }
            else if (ch.toString().equals(")")) {
                numClosedBrackets++;
            }
        }

        if (numOpenedBrackets < numClosedBrackets) {
            // returns index of redundant bracket
            int bracketsLeft = numClosedBrackets - numOpenedBrackets;

            for (int i = 0; i < expression.length(); i++){
                char ch = expression.charAt(i);

                if (bracketsLeft > 0 && Character.toString(ch).equals(")")){
                    bracketsLeft--;
                }
                else if (bracketsLeft == 0 && Character.toString(ch).equals(")")) {
                    index = i;
                    break;
                }
            }
        }
        else if (numOpenedBrackets > numClosedBrackets) {
            // returns index of redundant bracket
            int bracketsLeft = numOpenedBrackets - numClosedBrackets;

            for (int i = 0; i < expression.length(); i++){
                char ch = expression.charAt(i);

                if (bracketsLeft > 0 && Character.toString(ch).equals("(")){
                    bracketsLeft--;
                }
                else if (bracketsLeft == 0 && Character.toString(ch).equals("(")) {
                    index = i;
                    break;
                }
            }
        }

        return index;
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

