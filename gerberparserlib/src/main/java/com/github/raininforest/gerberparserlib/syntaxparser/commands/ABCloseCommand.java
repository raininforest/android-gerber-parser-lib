package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class ABCloseCommand extends GerberCommand{
    public ABCloseCommand(int stringNumber) {
        super(stringNumber, GerberCommandName.AB);
        log.trace("AB close command created");
    }
}
