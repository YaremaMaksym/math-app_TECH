package org.xsakon.expression;

import java.util.ArrayList;
import java.util.List;

public class Expression {
    private Long id;
    private final String expression;
    private List<Double> rootValues;

    public Expression(String expression) {
        this.expression = expression;
        this.rootValues = new ArrayList<>();
    }

    public Expression(Long id, String expression) {
        this.id = id;
        this.expression = expression;
        this.rootValues = new ArrayList<>();
    }

    public void addRoot(double root) {
        rootValues.add(root);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getExpression() {
        return expression;
    }

    public List<Double> getRootValues(){
        List<Double> list = rootValues;
        return list;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "id=" + id +
                ", expression='" + expression + '\'' +
                '}';
    }
}
