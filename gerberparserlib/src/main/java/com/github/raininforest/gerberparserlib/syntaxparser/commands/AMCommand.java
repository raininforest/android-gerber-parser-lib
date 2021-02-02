package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.MacroBodyItem;

import java.util.List;

public class AMCommand extends GerberCommand {
    private String macroName;
    private List<MacroBodyItem> macroBody;

    public AMCommand(String macroName, List<MacroBodyItem> macroBody, int stringNumber) {
        super(stringNumber, GerberCommandName.AM);
        this.macroName = macroName;
        this.macroBody = macroBody;
        log.trace("AMCommand created");
    }

    public String getMacroName() {
        return macroName;
    }

    public void setMacroName(String macroName) {
        this.macroName = macroName;
    }

    public List<MacroBodyItem> getMacroBody() {
        return macroBody;
    }

    public void setMacroBody(List<MacroBodyItem> macroBody) {
        this.macroBody = macroBody;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        resultString.append("AM command{\n");
        resultString.append("   MacroName: " + macroName + '\n');
        resultString.append("   MacroBody: " + '\n');
        for (MacroBodyItem item : macroBody) {
            resultString.append("       " + item.toString() + '\n');
        }
        resultString.append("}\n");
        return resultString.toString();
    }
}
