package org.xsakon;
import org.xsakon.expression.Expression;
import org.xsakon.expression.ExpressionDao;
import org.xsakon.expression.ExpressionValidator;

import java.util.Scanner;

// example expressions
// 1+(3-4)-25(2*(3-5))
// 24+(x-2)+4=24*3/(x*-4)

public class Main {
    public static void main(String[] args) {
        ExpressionDao expressionDao = new ExpressionDao();
        Scanner scanner = new Scanner(System.in);

        boolean keepLooping = true;

        while (keepLooping) {
            try {
                displayMenu();
                System.out.print("Choice: ");
                String choice = scanner.nextLine();
                switch (Integer.parseInt(choice)) {
                    case 1 -> enterExpressions(expressionDao);
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

    private static void enterExpressions(ExpressionDao expressionDao) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter your expression: ");
        String expressionStr = scanner.nextLine();

        if (ExpressionValidator.checkCorrectness(expressionStr)){
            expressionDao.save(new Expression(expressionStr));
            System.out.println("Expression saved");
        }
    }

}

