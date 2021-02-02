package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class TDCommand extends GerberCommand{
    public TDCommand(int stringNumber) {
        super(stringNumber, GerberCommandName.TD);
    }
    //TODO
}
