package org.xsakon;
import net.sourceforge.jeval.Evaluator;
import org.xsakon.expression.Expression;
import org.xsakon.expression.ExpressionDao;
import org.xsakon.expression.ExpressionEvaluator;
import org.xsakon.expression.ExpressionValidator;
import org.xsakon.root.Root;
import org.xsakon.root.RootDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

// example expressions
// 3+(10-2*3)=28*x/(2*(5-6)) x=-0.5
// 9-2*3=8*x*(3+2)/(2*(13-1)) x=9/5
// x*x-4*x+3=0

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
                    case 2 -> findByMultipleRoots(rootDao);
                    case 3 -> findAllWithSingleRoot(rootDao);
                    case 4 -> keepLooping = false;
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
                1️⃣ - Enter expression Menu
                2️⃣ - Find expressions by roots
                3️⃣ - Find expressions with one root
                4️⃣ - Exit
                """);
    }

    private static void displayExpressionMenu() {
        System.out.println("""
                \n
                Expression Menu:
                1️⃣ - Enter expression
                2️⃣ - Enter root for current expression
                3️⃣ - Exit to main menu
                """);
    }

    private static void displayExpressionsTable(List<Expression> expressions) {
        String idText = "ID";
        String expressionText = "Expression";
        String rootsText = "Roots";

        int idWidth = idText.length();
        int expressionWidth = expressionText.length();
        int rootsWidth = rootsText.length();

        for (Expression expression : expressions) {
            idWidth = Math.max(idWidth, String.valueOf(expression.getId()).length());
            expressionWidth = Math.max(expressionWidth, expression.getExpression().length());

            StringBuilder rootsStringBuilder = new StringBuilder();
            for (Double root : expression.getRootValues()) {
                rootsStringBuilder.append(root).append(", ");
            }
            rootsWidth = Math.max(rootsWidth, rootsStringBuilder.toString().trim().length());
        }

        String headerFormat = "| %-" + idWidth + "s | %-" + expressionWidth + "s | %-" + rootsWidth + "s |";
        String divider = "+" + "-".repeat(idWidth + 2) + "+" + "-".repeat(expressionWidth + 2) + "+" + "-".repeat(rootsWidth + 2) + "+";
        String rowFormat = "| %-" + idWidth + "d | %-"+ expressionWidth + "s | %-"+ rootsWidth +"s |";

        System.out.println("\nResults of search:");
        System.out.println(divider);
        System.out.printf((headerFormat) + "%n", "ID", "Expression", "Roots");
        System.out.println(divider);

        for (Expression expression : expressions) {
            String rootsListToString = expression.getRootValues().stream().map(Object::toString).collect(Collectors.joining(", "));
            System.out.printf((rowFormat) + "%n", expression.getId(), expression.getExpression(), rootsListToString);
        }

        System.out.println(divider);

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
                            enterRootForExpression(rootDao, expression);
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

    private static void enterRootForExpression(RootDao rootDao, Expression expression) {
        double rootValue = enterRoot();

        String expressionWithInsertedXVal = expression.getExpression().replace("x", Double.toString(rootValue));

        if (ExpressionEvaluator.checkIfRootIsCorrect(expressionWithInsertedXVal)){
            Root root = new Root(expression.getId(), rootValue);
            rootDao.save(root);
            System.out.println("Root saved");
        } else {
            System.out.println("Root is wrong");
        }
    }

    public static Double enterRoot() {
        Scanner scanner = new Scanner(System.in);
        Double rootValue;

        while (true) {
            try {
                System.out.println("\nEnter root: ");
                String input = scanner.nextLine();
                rootValue = Double.valueOf(new Evaluator().evaluate(input));
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage() + ". Please enter a number");
            }
        }

        return rootValue;
    }

    public static List<Double> enterMultipleRoots() {
        Scanner scanner = new Scanner(System.in);
        List<Double> rootValues = new ArrayList<>();
        String stringRootValues;

        while (true) {
            try {
                System.out.println("\nEnter one or more roots separated by spaces: ");
                stringRootValues = scanner.nextLine();

                String[] rootValuesArray = stringRootValues.split(" ");

                for (String value : rootValuesArray) {
                    rootValues.add(Double.valueOf(new Evaluator().evaluate(value)));
                }

                break;
            } catch (Exception e) {
                System.out.println(e.getMessage() + ". Please enter a number");
            }
        }

        return rootValues;
    }

    private static void findByMultipleRoots(RootDao rootDao) {

        List<Double> rootValues = enterMultipleRoots();
        List<Expression> expressions = rootDao.selectAllByRoots(rootValues);

        displayExpressionsTable(expressions);
    }

    private static void findAllWithSingleRoot(RootDao rootDao) {

        List<Expression> expressions = rootDao.selectAllWithSingleRoot();
        displayExpressionsTable(expressions);
    }

}

