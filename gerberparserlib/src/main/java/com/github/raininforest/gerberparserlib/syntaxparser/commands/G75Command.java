package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class G75Command extends GerberCommand {
    public G75Command(int stringNumber) {
        super(stringNumber, GerberCommandName.G75);
        log.trace("G75 command created");
    }
}
