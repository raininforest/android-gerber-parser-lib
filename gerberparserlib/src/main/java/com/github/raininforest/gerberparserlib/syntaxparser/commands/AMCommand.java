package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.MacroBodyItem;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.Variable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AMCommand extends GerberCommand {
    private final String macroName;
    private final List<MacroBodyItem> macroBody;
    private final HashMap<Integer, Variable> varDictionary;

    public AMCommand(String macroName,
                     List<MacroBodyItem> macroBody,
                     HashMap<Integer, Variable> varDictionary,
                     int stringNumber) {
        super(stringNumber, GerberCommandName.AM);
        this.macroName = macroName;
        this.macroBody = macroBody;
        this.varDictionary = varDictionary;
        log.trace("AMCommand created");
    }

    public Map<Integer, Variable> getVarDictionary(){
        return Collections.unmodifiableMap(varDictionary);
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
