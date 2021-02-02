package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class SRCloseCommand extends GerberCommand{
    public SRCloseCommand(int stringNumber) {
        super(stringNumber, GerberCommandName.SR);
        log.trace("SR close command created");
    }

    @Override
    public String toString() {
        return "SRCloseCommand";
    }
}
