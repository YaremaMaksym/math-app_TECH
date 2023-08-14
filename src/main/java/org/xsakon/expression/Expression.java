package org.xsakon.expression;

public class Expression {
    private Long id;
    private final String expression;

    public Expression(String expression) {
        this.expression = expression;
    }

    public Expression(Long id, String expression) {
        this.id = id;
        this.expression = expression;
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

    @Override
    public String toString() {
        return "Expression{" +
                "id=" + id +
                ", expression='" + expression + '\'' +
                '}';
    }
}
