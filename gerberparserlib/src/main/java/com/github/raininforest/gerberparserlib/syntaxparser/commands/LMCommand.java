package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;
import com.github.raininforest.gerberparserlib.enums.Mirroring;


public class LMCommand extends GerberCommand{
    private final Mirroring mirroring;

    public LMCommand(Mirroring mirroring, int stringNumber) {
        super(stringNumber, GerberCommandName.LM);
        this.mirroring = mirroring;
        log.trace("LM command created");
    }

    public Mirroring getMirroring() {
        return mirroring;
    }

    @Override
    public String toString() {
        return "LMCommand{" +
                "mirroring=" + mirroring +
                '}';
    }
}
