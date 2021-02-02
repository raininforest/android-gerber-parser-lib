package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class G01Command extends GerberCommand {
    public G01Command(int stringNumber) {
        super(stringNumber, GerberCommandName.G01);
        log.trace("G01 command created");
    }
}
