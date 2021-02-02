package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class G37Command extends GerberCommand{
    public G37Command(int stringNumber) {
        super(stringNumber, GerberCommandName.G37);
        log.trace("G37 command created");
    }
}