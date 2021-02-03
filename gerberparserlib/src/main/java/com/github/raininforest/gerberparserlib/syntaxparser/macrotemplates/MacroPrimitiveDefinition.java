package com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates;

import java.util.List;

/**
 * Represents primitive definition
 *
 * @author Sergey Velesko
 */
public class MacroPrimitiveDefinition extends MacroBodyItem {
    private final int primitiveCode;
    private final List<MacroExpression> modifiers;

    public MacroPrimitiveDefinition(int primitiveCode, List<MacroExpression> modifiers) {
        this.primitiveCode = primitiveCode;
        this.modifiers = modifiers;
        log.trace("MacroPrimitiveDefinition {" + this + "} created");
    }

    public int getPrimitiveCode() {
        return primitiveCode;
    }

    public List<MacroExpression> getModifiers() {
        return modifiers;
    }

    @Override
    public String toString() {
        return "{primitiveCode=" + primitiveCode + ", modifiers count=" + modifiers.size() + '}';
    }
}
