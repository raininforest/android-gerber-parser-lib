package com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates;

import com.github.raininforest.gerberparserlib.enums.ExpressionItemType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents abstract arithmetic expression item (Operand or Operator)
 *
 * @author Sergey Velesko
 */
public abstract class ExpressionItem {
    protected static final Logger log = LogManager.getLogger();
    protected ExpressionItemType expressionItemType = ExpressionItemType.NO_ITEM_TYPE;

    public final ExpressionItemType getExpressionItemType() {
        return expressionItemType;
    }

    @Override
    public String toString() {
        return expressionItemType.toString();
    }
}
