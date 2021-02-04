package com.github.raininforest.gerberparserlib.syntaxparser.commands;


import com.github.raininforest.gerberparserlib.enums.GerberCommandName;
import com.github.raininforest.gerberparserlib.enums.Polarity;

public class LPCommand extends GerberCommand{
    private final Polarity polarity;

    public LPCommand(Polarity polarity, int stringNumber) {
        super(stringNumber, GerberCommandName.LP);
        this.polarity = polarity;
        log.trace("LP command created");
    }

    public Polarity getPolarity() {
        return polarity;
    }
}
