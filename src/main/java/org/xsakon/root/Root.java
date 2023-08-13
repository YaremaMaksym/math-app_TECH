package org.xsakon.root;

public class Root {
    private Long id;
    private Long expressionId;
    private Double value;

    public Root(Long id, Long expressionId, double value) {
        this.id = id;
        this.expressionId = expressionId;
        this.value = value;
    }

    public Root(Long expressionId, double value) {
        this.expressionId = expressionId;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Long getExpressionId() {
        return expressionId;
    }

    public double getValue() {
        return value;
    }
}
