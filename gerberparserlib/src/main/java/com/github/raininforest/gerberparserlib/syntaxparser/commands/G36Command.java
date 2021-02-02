package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class G36Command extends GerberCommand{
    public G36Command(int stringNumber) {
        super(stringNumber, GerberCommandName.G36);
        log.trace("G36 command created");
    }
}