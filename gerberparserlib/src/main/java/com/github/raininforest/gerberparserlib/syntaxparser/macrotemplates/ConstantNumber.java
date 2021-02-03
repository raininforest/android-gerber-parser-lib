package com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates;

import com.github.raininforest.gerberparserlib.enums.ExpressionItemType;

/**
 * Represents double number. Can be used in expressions as Operand
 *
 * @author Sergey Velesko
 */
public class ConstantNumber extends Operand {

    public ConstantNumber(double value) {
        this.expressionItemType = ExpressionItemType.OPERAND;
        this.value = value;
        log.trace("ConstantNumber {value=" + this.value + "} created");
    }

    @Override
    public String toString() {
        return "Constant {value=" + value + "}";
    }
}
