package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;
import com.github.raininforest.gerberparserlib.exceptions.WrongLoadScalingException;

public class LSCommand extends GerberCommand {
    private final double scaleFactor;

    public LSCommand(double scaleFactor, int stringNumber) throws WrongLoadScalingException {
        super(stringNumber, GerberCommandName.LS);
        if (scaleFactor <= 0.0) {
            log.error("WrongLoadScalingException. String number=" + this.stringNumber);
            throw new WrongLoadScalingException();
        }
        this.scaleFactor = scaleFactor;
        log.trace("LS command created");
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    @Override
    public String toString() {
        return "LSCommand{" +
                "scaleFactor=" + scaleFactor +
                '}';
    }
}
