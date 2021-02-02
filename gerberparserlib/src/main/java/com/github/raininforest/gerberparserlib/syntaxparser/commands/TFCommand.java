package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class TFCommand extends GerberCommand {
    public TFCommand(int stringNumber) {
        super(stringNumber, GerberCommandName.TF);
    }
    //TODO
}
