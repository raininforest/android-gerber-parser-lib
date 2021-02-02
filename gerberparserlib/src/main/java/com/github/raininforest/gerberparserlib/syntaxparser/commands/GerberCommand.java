package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GerberCommand {
    protected static final Logger log = LogManager.getLogger();
    protected final int stringNumber;
    protected final GerberCommandName gerberCommandName;

    protected GerberCommand(int stringNumber, GerberCommandName gerberCommandName) {
        this.stringNumber = stringNumber;
        this.gerberCommandName = gerberCommandName;
    }

    public final int getStringNumber() {
        return stringNumber;
    }

    public final GerberCommandName getGerberCommandName() {
        return gerberCommandName;
    }

    @Override
    public String toString() {
        return gerberCommandName.toString();
    }
}
