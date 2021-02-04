package com.github.raininforest.gerberparserlib.enums;

public enum ArithmeticOperation {
    UNARY_PLUS(2),
    BINARY_PLUS(1),
    UNARY_MINUS(2),
    BINARY_MINUS(1),
    MULTIPLY(2),
    DIVIDE(2),
    OPENING_PARENTHESIS,
    CLOSING_PARENTHESIS;

    private int priority;

    ArithmeticOperation() {
    }

    ArithmeticOperation(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}

