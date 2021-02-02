package com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates;

public abstract class Operand extends ExpressionItem {
    protected double value;

    public final double getValue() {
        return value;
    }
}
