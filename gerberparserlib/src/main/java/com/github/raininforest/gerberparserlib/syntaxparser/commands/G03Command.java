package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class G03Command extends GerberCommand {
    public G03Command(int stringNumber) {
        super(stringNumber, GerberCommandName.G03);
        log.trace("G03 command created");
    }
}