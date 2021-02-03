package com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates;

import com.github.raininforest.gerberparserlib.enums.MacroItemType;

/**
 * Represents variable definition
 *
 * @author Sergey Velesko
 */
public class MacroVariableDefinition extends MacroBodyItem {
    private final Variable variable;
    private final MacroExpression expression;

    public MacroVariableDefinition(Variable variable, MacroExpression expression) {
        this.variable = variable;
        this.expression = expression;
        this.macroItemType = MacroItemType.VARIABLE_DEFINITION;
        log.trace("VariableDefinition {" + this + "} created");
    }

    public Variable getVariable() {
        return variable;
    }

    public MacroExpression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "{var index=" + variable.getIndex() + ", expression=" + expression + '}';
    }
}
