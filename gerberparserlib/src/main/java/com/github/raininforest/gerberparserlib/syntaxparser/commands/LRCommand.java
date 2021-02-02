package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

public class LRCommand extends GerberCommand{
    //The rotation angle, in degrees, counterclockwise.
    private double rotation;

    public LRCommand(double rotation, int stringNumber) {
        super(stringNumber, GerberCommandName.LR);
        this.rotation = rotation;
        log.trace("LR command created");
    }

    public double getRotation() {
        return rotation;
    }

    @Override
    public String toString() {
        return "LRCommand{" +
                "rotation=" + rotation +
                '}';
    }
}
