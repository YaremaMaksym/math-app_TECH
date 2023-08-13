package org.xsakon.expression;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public class ExpressionEvaluator {

    public static boolean checkIfRootIsCorrect(String expressionStr) {
        String[] parts = expressionStr.split("=", 2);

        try {
            double leftPartRes = Double.parseDouble(new Evaluator().evaluate(parts[0]));
            double rightPartRes = Double.parseDouble(new Evaluator().evaluate(parts[1]));
            double res;

            if (leftPartRes > rightPartRes) {
                res = leftPartRes - rightPartRes;
            } else if (leftPartRes < rightPartRes) {
                res = rightPartRes - leftPartRes;
            } else {
                return true;
            }

            return (res < 0.000_000_001) && res > 0;

        } catch (EvaluationException e) {
            e.printStackTrace();
            return false;
        }
    }
}
