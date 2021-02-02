package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class G02Command extends GerberCommand{
    public G02Command(int stringNumber) {
        super(stringNumber, GerberCommandName.G02);
        log.trace("G02 command created");
    }
}