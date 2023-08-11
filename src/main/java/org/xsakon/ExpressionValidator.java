package org.xsakon;

import java.util.Optional;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionValidator {
    public static void checkCorrectness(String expression) {
        boolean isCorrect = true;

        Optional<Integer> wrongBracketIndex = checkBracketCorrectness(expression);
        if (wrongBracketIndex.isPresent()) {
            highlightError(expression, "Wrong bracket placement", wrongBracketIndex.get());
            isCorrect = false;
        }

        Optional<Integer> wrongCharIndex = checkIfHasInvalidCharacters(expression);
        if (wrongCharIndex.isPresent()) {
            highlightError(expression, "Expression has invalid characters", wrongCharIndex.get());
            isCorrect = false;
        }

        if (isCorrect) {
            System.out.println("Expression is correct");
        }
    }

    private static Optional<Integer> checkIfHasInvalidCharacters(String expression) {
        String regex = "[^0-9x=+\\-*/().]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);

        if (matcher.find()) {
            return Optional.of(matcher.start());
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Integer> checkBracketCorrectness(String expression) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(') {
                stack.push(i);
            } else if (ch == ')') {
                if (stack.isEmpty()) {
                    return Optional.of(i);  // Redundant closing bracket
                }
                stack.pop();
            }
        }

        if (!stack.isEmpty()) {
            return Optional.of(stack.peek());  // Redundant opening bracket
        }

        return Optional.empty(); // All brackets correct
    }

    private static void highlightError(String expression, String message, int errorIndex) {
        System.out.println("Error: " + message);
        System.out.println(expression);
        for (int i = 0; i < errorIndex; i++) {
            System.out.print(" ");
        }
        System.out.println("^ Here");
    }
}
