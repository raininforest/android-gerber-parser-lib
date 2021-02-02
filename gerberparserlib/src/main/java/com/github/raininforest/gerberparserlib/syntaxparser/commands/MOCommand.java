package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;
import com.github.raininforest.gerberparserlib.enums.Units;

public class MOCommand extends GerberCommand {
    private final Units units;

    public MOCommand(Units units, int stringNumber) {
        super(stringNumber, GerberCommandName.MO);
        this.units = units;
        log.trace("MO command created");
    }

    public Units getUnits() {
        return units;
    }

    @Override
    public String toString() {
        return "MOCommand{" +
                "units=" + units +
                '}';
    }
}
