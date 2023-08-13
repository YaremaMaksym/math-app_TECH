package org.xsakon;
import org.xsakon.expression.Expression;
import org.xsakon.expression.ExpressionDao;
import org.xsakon.expression.ExpressionEvaluator;
import org.xsakon.expression.ExpressionValidator;
import org.xsakon.root.Root;
import org.xsakon.root.RootDao;

import java.util.Optional;
import java.util.Scanner;

// example expressions
// 1+(3-4)=25/(2*(3-5*x))
// 24+(x-2)+4=24*3/(x*-4)

public class Main {
    public static void main(String[] args) {
        ExpressionDao expressionDao = new ExpressionDao();
        RootDao rootDao = new RootDao();
        Scanner scanner = new Scanner(System.in);

        boolean keepLooping = true;

        while (keepLooping) {
            try {
                displayMainMenu();
                System.out.print("Choice: ");
                String choice = scanner.nextLine();
                switch (Integer.parseInt(choice)) {
                    case 1 -> enterExpressionMenu(expressionDao, rootDao);
                    case 2 -> keepLooping = false;
                    default -> System.out.println(choice + " not a valid option");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + ". Please enter a number");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("""
                \n
                Main Menu:
                1️⃣ - Enter Expression Menu
                2️⃣ - Exit
                """);
    }

    private static void displayExpressionMenu() {
        System.out.println("""
                \n
                Expression Menu:
                1️⃣ - Enter Expression
                2️⃣ - Enter root for current expression
                3️⃣ - Exit to Main Menu
                """);
    }

    private static void enterExpressionMenu(ExpressionDao expressionDao, RootDao rootDao) {
        Scanner scanner = new Scanner(System.in);
        Expression expression = null;
        boolean keepLooping = true;

        while (keepLooping) {
            try {
                displayExpressionMenu();

                if (expression != null){
                    System.out.println("Current expression: " + expression.getExpression() + "\n");
                }

                System.out.print("Choice: ");
                String choice = scanner.nextLine();
                switch (Integer.parseInt(choice)) {
                    case 1 -> {
                        Optional<Expression> expressionOptional = enterExpression(expressionDao);
                        if (expressionOptional.isPresent()){
                            expression = expressionOptional.get();
                        }
                    }
                    case 2 -> {
                        if (expression != null){
                            enterRoot(rootDao, expression);
                        } else {
                            System.out.println("First enter a valid expression");
                        }
                    }
                    case 3 -> keepLooping = false;
                    default -> System.out.println(choice + " not a valid option");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + ". Please enter a number");
            }
        }
    }

    private static Optional<Expression> enterExpression(ExpressionDao expressionDao) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter your expression: ");
        String expressionStr = scanner.nextLine();

        if (ExpressionValidator.checkCorrectness(expressionStr)){
            Expression expression = new Expression(expressionStr);

            Optional<Long> expressionIdOptional = expressionDao.save(expression);
            if(expressionIdOptional.isPresent()){
                expression.setId(expressionIdOptional.get());
            } else {
                System.out.println("Error during saving an object in db");
                return Optional.empty();
            }

            System.out.println("Expression saved");

            return Optional.of(expression);
        } else {
            return Optional.empty();
        }
    }

    private static void enterRoot(RootDao rootDao, Expression expression) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter root: ");
        Double rootValue = scanner.nextDouble();

        String expressionWithInsertedXVal = expression.getExpression().replace("x", Double.toString(rootValue));

        if (ExpressionEvaluator.checkIfRootIsCorrect(expressionWithInsertedXVal)){
            Root root = new Root(expression.getId(), rootValue);
            rootDao.save(root);
            System.out.println("Root saved");
        } {
            System.out.println("Root is wrong");
        }
    }

}

