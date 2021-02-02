package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;
import com.github.raininforest.gerberparserlib.exceptions.WrongSRParametersException;

public class SROpenCommand extends GerberCommand {
    //Defines the number of times the block is repeated along the X axis. integer ≥ 1
    private final int XRepeats;
    //Defines the number of times the block is repeated along the Y axis. integer ≥ 1
    private final int YRepeats;
    //Defines the step distance along the X axis. decimal number ≥ 0
    private final double IDistance;
    //Defines the step distance along the Y axis. decimal number ≥ 0
    private final double JDistance;

    public SROpenCommand(int XRepeats, int YRepeats, double IDistance, double JDistance, int stringNumber) throws WrongSRParametersException {
        super(stringNumber, GerberCommandName.SR);
        this.XRepeats = XRepeats;
        this.YRepeats = YRepeats;
        this.IDistance = IDistance;
        this.JDistance = JDistance;
        boolean wrongParameters = (XRepeats < 1) ||
                (YRepeats < 1) ||
                (IDistance < 0) ||
                (JDistance < 0);
        if (wrongParameters) {
            log.error("WrongSRParametersException. String number=" + this.stringNumber);
            throw new WrongSRParametersException();
        }
        log.trace("SR command created");
    }

    public int getXRepeats() {
        return XRepeats;
    }

    public int getYRepeats() {
        return YRepeats;
    }

    public double getIDistance() {
        return IDistance;
    }

    public double getJDistance() {
        return JDistance;
    }

    @Override
    public String toString() {
        return "SROpenCommand{" +
                "XRepeats=" + XRepeats +
                ", YRepeats=" + YRepeats +
                ", IDistance=" + IDistance +
                ", JDistance=" + JDistance +
                '}';
    }
}
