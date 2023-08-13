package org.xsakon.expression;

import java.util.Optional;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionValidator {
    public static boolean checkCorrectness(String expression) {
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

        Optional<Integer> wrongOperationIndex = checkOperationsCorrectness(expression);
        if (wrongOperationIndex.isPresent()) {
            highlightError(expression, "Expression has invalid operation", wrongOperationIndex.get());
            isCorrect = false;
        }

        Optional<String> equalSignErrorMessage = checkIfHasValidEqualSignUsage(expression);
        if (equalSignErrorMessage.isPresent()) {
            System.out.println("Error: " + equalSignErrorMessage.get());
            System.out.println(expression);
            isCorrect = false;
        }

        Optional<String> containsXErrorMessage = checkIfContainsX(expression);
        if (containsXErrorMessage.isPresent()) {
            System.out.println("Error: " + containsXErrorMessage.get());
            System.out.println(expression);
            isCorrect = false;
        }

        return isCorrect;
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

    private static Optional<Integer> checkOperationsCorrectness(String expression) {
        String[] invalidPatterns = {
                "\\d+\\.\\d*\\.",           // more than one "." in numbers
                "^[*.=/]",                  // expression starts with * . / =
                "[+\\-*.=/]$",              // expression ends with + - * . / =
                "[\\-+.]{2,}",              // more than one + or -
                "[*/.]{2,}",                // more than one * / .
                "[x]{2,}",                  // two or more "x" together
//                "\\d+\\(",                  // digit right before closed brackets
//                "\\)\\d+",                  // digit right after open brackets
//                "\\)x",                     // x right before closed bracket
//                "x\\(",                     // x right after open bracket
                "x\\d+",                    // x right before digits
                "\\d+x\\d+",                // x in between digits
                "\\d+x",                    // x right after digit
                "[0-9x]\\(",           // x or digit right before open bracket
                "\\)[0-9x]",               // x or digit right after closed brackets
                "[+\\-*./=]\\)",            // + - * . / = right before closed bracket
                "\\([+\\-*./=]\\(",          // + - * . / = between open brackets
                "\\)\\(",                   // open bracket right after closed bracket
                "\\(\\)"                    // empty brackets
        };

        for (String patternStr : invalidPatterns) {
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(expression);
            if (matcher.find()) {
                return Optional.of(matcher.start());
            }
        }

        return Optional.empty();
    }

    private static Optional<String> checkIfHasValidEqualSignUsage(String expression) {
        if (expression.indexOf('=') != expression.lastIndexOf('=')) {
            return Optional.of("Expression has more than one \"=\" sign");
        } else if (!expression.contains("=")) {
            return Optional.of("Expression doesn't have \"=\" sign");
        } else {
            return Optional.empty();
        }
    }

    private static Optional<String> checkIfContainsX(String expression) {
        if (!expression.contains("x")) {
            return Optional.of("Expression doesn't have \"x\"");
        } else {
            return Optional.empty();
        }
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
