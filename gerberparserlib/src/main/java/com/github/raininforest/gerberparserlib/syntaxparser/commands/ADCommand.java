package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.ApertureTemplateType;
import com.github.raininforest.gerberparserlib.enums.GerberCommandName;
import com.github.raininforest.gerberparserlib.exceptions.WrongApertureNumberException;

import java.util.List;

public class ADCommand extends GerberCommand {
    private final int apertureId;
    private final ApertureTemplateType apertureTemplateType;
    private final String apertureTemplateName;
    private final List<Double> parameterList;

    public ADCommand(int apertureId,
                     ApertureTemplateType standartApertureTemplateType,
                     List<Double> parameterList,
                     int stringNumber) throws WrongApertureNumberException {
        super(stringNumber, GerberCommandName.AD);
        if (apertureId < 10) {
            log.error("WrongApertureNumberException. String number=" + this.stringNumber);
            throw new WrongApertureNumberException();
        }
        this.apertureId = apertureId;
        this.apertureTemplateType = standartApertureTemplateType;
        this.apertureTemplateName = standartApertureTemplateType.toString();
        this.parameterList = parameterList;
        log.trace("ADCommand (STANDART) created");
    }

    public ADCommand(int apertureId,
                     String macroApertureTemplateName,
                     List<Double> parameterList,
                     int stringNumber) throws WrongApertureNumberException {
        super(stringNumber, GerberCommandName.AD);
        if (apertureId < 10) {
            log.error("WrongApertureNumberException. String number=" + this.stringNumber);
            throw new WrongApertureNumberException();
        }
        this.apertureId = apertureId;
        this.apertureTemplateType = ApertureTemplateType.MACRO;
        this.apertureTemplateName = macroApertureTemplateName;
        this.parameterList = parameterList;
        log.trace("ADCommand (MACRO) created");
    }

    public String getApertureTemplateName() {
        return apertureTemplateName;
    }

    public int getApertureId() {
        return apertureId;
    }

    public ApertureTemplateType getApertureTemplateType() {
        return apertureTemplateType;
    }

    public List<Double> getParameterList() {
        return parameterList;
    }

    @Override
    public String toString() {
        return "ADCommand{" +
                "apertureId=" + apertureId +
                ", apertureTemplateType=" + apertureTemplateType +
                ", apertureTemplateName='" + apertureTemplateName + '\'' +
                ", parameterList=" + parameterList +
                '}';
    }
}
