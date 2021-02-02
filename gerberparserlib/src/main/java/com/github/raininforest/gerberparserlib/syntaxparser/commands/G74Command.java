package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class G74Command extends GerberCommand{
    public G74Command(int stringNumber) {
        super(stringNumber, GerberCommandName.G74);
        log.trace("G74 command created");
    }
}
