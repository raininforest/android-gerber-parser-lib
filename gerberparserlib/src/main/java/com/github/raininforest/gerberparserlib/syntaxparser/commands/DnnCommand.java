package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;
import com.github.raininforest.gerberparserlib.exceptions.WrongApertureNumberException;

public class DnnCommand extends GerberCommand{
    private int apertureId;

    public DnnCommand(int apertureId, int stringNumber) throws WrongApertureNumberException {
        super(stringNumber, GerberCommandName.DNN);
        if (apertureId < 10) {
            log.error("WrongApertureNumberException. String number=" + this.stringNumber);
            throw new WrongApertureNumberException();
        }
        this.apertureId = apertureId;
        log.trace("Dnn command created");
    }

    public int getApertureId() {
        return apertureId;
    }

    @Override
    public String toString() {
        return "DnnCommand{" +
                "apertureId=" + apertureId +
                '}';
    }
}
