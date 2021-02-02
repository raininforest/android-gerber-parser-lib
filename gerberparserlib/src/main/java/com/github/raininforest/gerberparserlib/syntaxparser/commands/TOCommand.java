package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class TOCommand extends GerberCommand{
    public TOCommand(int stringNumber) {
        super(stringNumber, GerberCommandName.TO);
    }
    //TODO
}
