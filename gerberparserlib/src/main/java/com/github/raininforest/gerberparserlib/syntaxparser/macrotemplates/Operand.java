package com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates;

/**
 * Represents operand in arithmetic expressions
 *
 * @author Sergey Velesko
 */
public abstract class Operand extends ExpressionItem {
    protected double value;

    public final double getValue() {
        return value;
    }
}
