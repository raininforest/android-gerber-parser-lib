package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class M02Command extends GerberCommand {
    public M02Command(int stringNumber) {
        super(stringNumber, GerberCommandName.M02);
    }
}
