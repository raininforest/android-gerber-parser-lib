package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class FSCommand extends GerberCommand {
    private final int integerDigitCountX;
    private final int decimalDigitCountX;
    private final int integerDigitCountY;
    private final int decimalDigitCountY;

    public FSCommand(int integerDigitCountX, int decimalDigitCountX, int integerDigitCountY, int decimalDigitCountY, int stringNumber) {
        super(stringNumber, GerberCommandName.FS);
        this.integerDigitCountX = integerDigitCountX;
        this.decimalDigitCountX = decimalDigitCountX;
        this.integerDigitCountY = integerDigitCountY;
        this.decimalDigitCountY = decimalDigitCountY;
        log.trace("FS command created");
        if (decimalDigitCountX < 5) {
            log.warn("FS command: decimal digit count < 5! ");
        }
    }

    public int getIntegerDigitCountX() {
        return integerDigitCountX;
    }

    public int getDecimalDigitCountX() {
        return decimalDigitCountX;
    }

    public int getIntegerDigitCountY() {
        return integerDigitCountY;
    }

    public int getDecimalDigitCountY() {
        return decimalDigitCountY;
    }

    @Override
    public String toString() {
        return "FSCommand{" +
                "int=" + integerDigitCountX +
                ", dec=" + decimalDigitCountX + '}';
    }
}
