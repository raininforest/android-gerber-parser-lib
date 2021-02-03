package com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates;

import com.github.raininforest.gerberparserlib.enums.MacroItemType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents part of macro template definition (primitive or variable definition)
 *
 * @author Sergey Velesko
 */
public abstract class MacroBodyItem {
    protected static final Logger log = LogManager.getLogger();
    protected MacroItemType macroItemType;

    public final MacroItemType getMacroItemType() {
        return macroItemType;
    }

    @Override
    public String toString() {
        return macroItemType.toString();
    }
}
