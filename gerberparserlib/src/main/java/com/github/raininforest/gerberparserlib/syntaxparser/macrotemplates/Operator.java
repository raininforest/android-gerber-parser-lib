package com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates;

import com.github.raininforest.gerberparserlib.enums.ArithmeticOperation;
import com.github.raininforest.gerberparserlib.enums.ExpressionItemType;

/**
 * Represents operator in arithmetic expressions
 *
 * @author Sergey Velesko
 */
public class Operator extends ExpressionItem {
    private final ArithmeticOperation operation;

    public Operator(ArithmeticOperation operation) {
        this.expressionItemType = ExpressionItemType.OPERATOR;
        this.operation = operation;
        log.trace("Operator {operation=" + this.operation + "} created");
    }

    public ArithmeticOperation getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return operation.toString();
    }
}
