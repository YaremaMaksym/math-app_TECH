package org.xsakon.expression;

public class Expression {
    private Long id;
    private String expression;

    public Expression(Long id, String expression) {
        this.id = id;
        this.expression = expression;
    }

    public Expression(String expression) {
        this.expression = expression;
    }

    public Long getId() {
        return id;
    }

    public String getExpression() {
        return expression;
    }
}
