package com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates;

import com.github.raininforest.gerberparserlib.enums.ExpressionItemType;

/**
 * Represents variable in arithmetic expressions and macro templates parameters
 *
 * @author Sergey Velesko
 */
public class Variable extends Operand {
    private final int index;

    public Variable(int index) {
        this.expressionItemType = ExpressionItemType.OPERAND;
        this.index = index;
        this.value = 0;
        log.trace("Variable {index=" + this.index + ", value=" + this.value + "} created");
    }

    public void setValue(double value) {
        this.value = value;
        log.trace("Set variable value {index: " + this.index + ", value=" + this.value + "}");
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Variable {index=" + index + ", value=" + value + "}";
    }
}
