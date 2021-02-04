package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;
import com.github.raininforest.gerberparserlib.exceptions.WrongApertureNumberException;

public class ABOpenCommand extends GerberCommand{
    private final int apertureId;

    public ABOpenCommand(int apertureId, int stringNumber) throws WrongApertureNumberException{
        super(stringNumber, GerberCommandName.AB);
        this.apertureId = apertureId;
        if (apertureId < 10) {
            log.error("WrongApertureNumberException. String number=" + this.stringNumber);
            throw new WrongApertureNumberException();
        }
        log.trace("AB open command created");
    }

    public int getApertureId() {
        return apertureId;
    }

    @Override
    public String toString() {
        return "ABOpenCommand{" +
                "apertureId=" + apertureId +
                '}';
    }
}
