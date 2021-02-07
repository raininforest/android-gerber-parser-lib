package com.github.raininforest.gerberparserlib.syntaxparser;

import com.github.raininforest.gerberparserlib.enums.ApertureTemplateType;
import com.github.raininforest.gerberparserlib.enums.ArithmeticOperation;
import com.github.raininforest.gerberparserlib.enums.CoordinateType;
import com.github.raininforest.gerberparserlib.enums.GerberCommandName;
import com.github.raininforest.gerberparserlib.enums.Mirroring;
import com.github.raininforest.gerberparserlib.enums.Polarity;
import com.github.raininforest.gerberparserlib.enums.Units;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.ABCloseCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.ABOpenCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.ADCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.AMCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.Coordinate;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.D01Command;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.D02Command;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.D03Command;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.DnnCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.FSCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.G01Command;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.G02Command;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.G03Command;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.G36Command;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.G37Command;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.G74Command;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.G75Command;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.GerberCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.LMCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.LPCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.LRCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.LSCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.M02Command;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.MOCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.SRCloseCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.SROpenCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.TACommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.TDCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.TFCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.TOCommand;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.ConstantNumber;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.MacroBodyItem;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.MacroExpression;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.MacroPrimitiveDefinition;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.MacroVariableDefinition;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.Operator;
import com.github.raininforest.gerberparserlib.syntaxparser.macrotemplates.Variable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The SyntaxParser. Parses lines from gerber file (line by line from List of Strings).
 * Gives a list of gerber commands.
 *
 * @author Sergey Velesko
 */
public class SyntaxParser {
    private static final Logger log = LogManager.getLogger();
    private static final int DEFAULT_INT_DIGIT_COUNT = 3;
    private static final int DEFAULT_DEC_DIGIT_COUNT = 6;
    private static final int NOT_COMMENT_STRING_COUNT_TO_FIND_FS_MO = 10;

    private static final Pattern FS_PATTERN;
    private static final Pattern MO_PATTERN;
    private static final Pattern AD_PATTERN;
    private static final Pattern AM_PATTERN;
    private static final Pattern DNN_PATTERN;
    private static final Pattern COORDINATE_DATA;
    private static final Pattern LP_PATTERN;
    private static final Pattern LM_PATTERN;
    private static final Pattern LR_PATTERN;
    private static final Pattern LS_PATTERN;
    private static final Pattern AB_PATTERN;
    private static final Pattern SR_PARAMETERS;

    static {
        FS_PATTERN = Pattern.compile("^%FSLAX(\\d)(\\d)Y\\d+\\*%");
        MO_PATTERN = Pattern.compile("^%MO(MM|IN)\\*%");
        AD_PATTERN = Pattern.compile("^%ADD([1-9]\\d+)(\\w+),(.*)\\*%");
        AM_PATTERN = Pattern.compile("^%AM([a-zA-Z_$][a-zA-Z_.$0-9]*)(.*)\\*%");
        DNN_PATTERN = Pattern.compile("^(G54)?D([1-9][0-9]+)");
        COORDINATE_DATA = Pattern.compile("([XYIJ])([-+]?\\d+)");
        LP_PATTERN = Pattern.compile("^%LP([DC])\\*%");
        LM_PATTERN = Pattern.compile("^%LM(XY|Y|X|N)\\*%");
        LR_PATTERN = Pattern.compile("^%LR([-+]?[0-9]*\\.?[0-9]*)\\*%");
        LS_PATTERN = Pattern.compile("^%LS([0-9]*\\.?[0-9]*)\\*%");
        AB_PATTERN = Pattern.compile("^%ABD?([1-9][0-9]+)?\\*%");
        SR_PARAMETERS = Pattern.compile("([XYIJ])(\\d*\\.?\\d+)");
    }

    private final String fileInfo;
    private final List<String> gerberFileStringList;
    private final List<GerberCommand> gerberCommands;
    private int lineIndex;
    private FormatSpecification coordinateFormat;
    private GerberCommandName deprecatedCurrentDCommand;

    public SyntaxParser(String fileInfo, List<String> gerberFileStringList) {
        this.fileInfo = fileInfo;
        this.gerberFileStringList = gerberFileStringList;
        this.gerberCommands = new ArrayList<>();
        this.deprecatedCurrentDCommand = GerberCommandName.D01;
        this.coordinateFormat = new FormatSpecification(DEFAULT_INT_DIGIT_COUNT, DEFAULT_DEC_DIGIT_COUNT);
        log.trace("SyntaxParser created");
        log.trace("Gerber file string list size=" + gerberFileStringList.size());
    }

    /**
     * Parsing into list of gerber commands
     * @return List<GerberCommand>
     */
    public List<GerberCommand> parse() {

        //checking is gerber file valid
        if (!isGerberFileValid()) {
            log.error("Gerber file " + fileInfo + " is invalid!");
            return null;
        }
        log.trace("start parsing...");

        //reading line by line
        lineIndex = 0;
        while (lineIndex < gerberFileStringList.size()) {
            String currentString = gerberFileStringList.get(lineIndex);
            log.trace("[  line:" + lineIndex + "  ] " + currentString);
            try {
                if (currentString.startsWith("G04")) {
                    log.info(currentString);
                } else if (currentString.startsWith("%")) {
                    log.trace("Extended command detected");
                    String command = currentString.substring(1, 3);
                    switch (command) {
                        case "FS":
                            gerberCommands.add(createFS(currentString));
                            break;
                        case "MO":
                            gerberCommands.add(createMO(currentString));
                            break;
                        case "AD":
                            gerberCommands.add(createAD(currentString));
                            break;
                        case "AM":
                            gerberCommands.add(createAM(currentString));
                            break;
                        case "AB":
                            gerberCommands.add(createAB(currentString));
                            break;
                        case "SR":
                            gerberCommands.add(createSR(currentString));
                            break;
                        case "LP":
                            gerberCommands.add(createLP(currentString));
                            break;
                        case "LM":
                            gerberCommands.add(createLM(currentString));
                            break;
                        case "LR":
                            gerberCommands.add(createLR(currentString));
                            break;
                        case "LS":
                            gerberCommands.add(createLS(currentString));
                            break;
                        case "TF":
                            gerberCommands.add(createTF());
                            break;
                        case "TA":
                            gerberCommands.add(createTA());
                            break;
                        case "TO":
                            gerberCommands.add(createTO());
                            break;
                        case "TD":
                            gerberCommands.add(createTD());
                            break;
                    }
                } else if (currentString.contains("X") ||
                        currentString.contains("Y") ||
                        currentString.contains("D")) {
                    log.trace("D-Code detected");
                    checkDeprecatedGCodeInOperation(currentString);
                    gerberCommands.add(createDCode(currentString));
                } else if (currentString.contains("G")) {
                    log.trace("G-command detected");
                    String command = currentString.substring(0, 2);
                    switch (command) {
                        case "G01":
                            gerberCommands.add(createG01());
                            break;
                        case "G02":
                            gerberCommands.add(createG02());
                            break;
                        case "G03":
                            gerberCommands.add(createG03());
                            break;
                        case "G36":
                            gerberCommands.add(createG36());
                            break;
                        case "G37":
                            gerberCommands.add(createG37());
                            break;
                        case "G74":
                            gerberCommands.add(createG74());
                            break;
                        case "G75":
                            gerberCommands.add(createG75());
                            break;
                    }
                } else if (currentString.contains("M02")) {
                    log.trace("[  END OF FILE  ]");
                    gerberCommands.add(new M02Command(lineIndex));
                    break;
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
                log.error("Illegal command format. File: " +  fileInfo + ". String=" + lineIndex);
            }
            lineIndex++;
        }

        printGerberCommands(gerberCommands);
        return gerberCommands;
    }

    /**
     * Checks if file contains FS and MO commands
     * @return true, if file is valid
     */
    private boolean isGerberFileValid() {
        int stringNumber = 0;
        int counterFS = 0;
        int counterMO = 0;
        Matcher matcherFS;
        Matcher matcherMO;
        boolean fileIsValid = false;

        int notCommentStringCounter = 0;
        while (notCommentStringCounter < NOT_COMMENT_STRING_COUNT_TO_FIND_FS_MO) {
            String currentString = gerberFileStringList.get(stringNumber);
            stringNumber++;
            if (currentString.startsWith("G04")) {
                continue;
            }
            notCommentStringCounter++;
            matcherFS = FS_PATTERN.matcher(currentString);
            if (matcherFS.find()) {
                counterFS++;
            }
            matcherMO = MO_PATTERN.matcher(currentString);
            if (matcherMO.find()) {
                counterMO++;
            }
            if ((counterFS == 1) && (counterMO == 1)) {
                fileIsValid = true;
                break;
            }
        }

        return fileIsValid;
    }

    /**
     * Print all gerber commands from output list
     * @param gerberCommands List of gerber commands
     */
    private void printGerberCommands(List<GerberCommand> gerberCommands) {
        log.trace("----------------------------------------------------------");
        log.trace("---------------   Gerber Command List:   -----------------");
        log.trace("----------------------------------------------------------");
        for (GerberCommand command : gerberCommands) {
            log.trace(command);
        }
    }

    private FSCommand createFS(String currentString) {
        Matcher matcher = FS_PATTERN.matcher(currentString);
        log.trace("FS match regexp find: " + matcher.find());
        int integerDigitCount = Integer.parseInt(matcher.group(1));
        int decimalDigitCount = Integer.parseInt(matcher.group(2));
        log.trace("FS int: " + integerDigitCount);
        log.trace("FS dec: " + decimalDigitCount);
        coordinateFormat = new FormatSpecification(integerDigitCount, decimalDigitCount);
        return new FSCommand(integerDigitCount, decimalDigitCount, integerDigitCount, decimalDigitCount, lineIndex);
    }

    private MOCommand createMO(String currentString) {
        Matcher matcher = MO_PATTERN.matcher(currentString);
        log.trace("MO match regexp find: " + matcher.find());
        String units = matcher.group(1);
        log.trace("MO units: " + units);
        if (units.equals("MM")) {
            return new MOCommand(Units.MM, lineIndex);
        } else if (units.equals("IN")) {
            return new MOCommand(Units.IN, lineIndex);
        }
        return null;
    }

    private ADCommand createAD(String currentString) {
        Matcher matcher = AD_PATTERN.matcher(currentString);
        log.trace("AD match regexp find: " + matcher.find());
        int apertureID = Integer.parseInt(matcher.group(1));
        String apertureTemplateName = matcher.group(2);
        List<Double> parameters = new ArrayList<>();
        for (String param : matcher.group(3).split("X")) {
            parameters.add(Double.parseDouble(param));
        }
        ApertureTemplateType apertureTemplateType;
        switch (apertureTemplateName) {
            case "C":
                apertureTemplateType = ApertureTemplateType.C;
                break;
            case "R":
                apertureTemplateType = ApertureTemplateType.R;
                break;
            case "O":
                apertureTemplateType = ApertureTemplateType.O;
                break;
            case "P":
                apertureTemplateType = ApertureTemplateType.P;
                break;
            default:
                return new ADCommand(apertureID, apertureTemplateName, parameters, lineIndex);
        }
        return new ADCommand(apertureID, apertureTemplateType, parameters, lineIndex);
    }

    private AMCommand createAM(String currentString) {
        //building AM string
        StringBuilder amStringBuilder = new StringBuilder(currentString);
        boolean endOfAMCommand = false;
        if (amStringBuilder.lastIndexOf("%") > 1) {
            endOfAMCommand = true;
        }
        while (!endOfAMCommand) {
            lineIndex++;
            String newLine = gerberFileStringList.get(lineIndex);
            amStringBuilder.append(newLine);
            if (newLine.contains("%")) {
                endOfAMCommand = true;
            }
        }
        log.trace(amStringBuilder);

        //match regexp
        Matcher matcher = AM_PATTERN.matcher(amStringBuilder);
        log.trace("AM match regexp find: " + matcher.find());
        String macroName = matcher.group(1);
        log.trace("macroName=" + macroName);

        String[] allAMStrings = amStringBuilder.toString().split("\\*");
        List<String> macroBodyItemStrings = new ArrayList<>(Arrays.asList(allAMStrings).subList(1, (allAMStrings.length - 1)));
        List<MacroBodyItem> macroBodyItems = new ArrayList<>();
        for (String definition : macroBodyItemStrings) {
            if ((definition.startsWith("$")) && (definition.contains("="))) {
                macroBodyItems.add(createAMVariableDefinition(definition));
            } else {
                macroBodyItems.add(createAMPrimitiveDefinition(definition));
            }
        }
        return new AMCommand(macroName, macroBodyItems, lineIndex);
    }

    /**
     * Parses AM primitive definition object for AM Command
     * @param definition string containing primitive definition
     * @return MacroPrimitiveDefinition
     */
    private MacroPrimitiveDefinition createAMPrimitiveDefinition(String definition) {
        String[] definitionItems = definition.split(",");
        log.trace("create AM primitive definition: " + Arrays.toString(definitionItems));

        int primitiveCode = Integer.parseInt(definitionItems[0]);

        List<MacroExpression> parameters = new ArrayList<>();
        for (int i = 1; i < definitionItems.length; i++) {
            parameters.add(createAMExpression(definitionItems[i]));
        }
        return new MacroPrimitiveDefinition(primitiveCode, parameters);
    }

    /**
     * Parses AM variable definition object for AM Command
     * @param definition string containing variable definition
     * @return MacroPrimitiveDefinition
     */
    private MacroVariableDefinition createAMVariableDefinition(String definition) {
        String[] definitionItems = definition.split("=");
        log.trace("Create AM var definition: " + Arrays.toString(definitionItems));

        StringBuilder varIndexBuilder = new StringBuilder(definitionItems[0].trim());
        varIndexBuilder.deleteCharAt(0);
        Variable variable = new Variable(Integer.parseInt(varIndexBuilder.toString()));

        MacroExpression expression = createAMExpression(definitionItems[1].trim());
        return new MacroVariableDefinition(variable, expression);
    }

    /**
     * Parses arithmetical expression from string (parameter)
     * Used in variable and primitive definition
     * @param parameter string to be parsed as arithmetical expression
     * @return MacroExpression
     */
    private MacroExpression createAMExpression(String parameter) {
        parameter = parameter.trim();
        log.trace("Creating expression from string: " + parameter);
        MacroExpression resultExpression = new MacroExpression();
        int i = 0;
        while (i < parameter.length()) {
            switch (parameter.charAt(i)) {
                case '-': {
                    boolean unaryMinus = (i == 0) || ((i > 0) && (!Character.isDigit(parameter.charAt(i - 1))));
                    if (unaryMinus) {
                        resultExpression.addExpressionItem(new Operator(ArithmeticOperation.UNARY_MINUS));
                    } else {
                        resultExpression.addExpressionItem(new Operator(ArithmeticOperation.BINARY_MINUS));
                    }
                }
                break;
                case '+': {
                    boolean unaryPlus = (i == 0) || ((i > 0) && (!Character.isDigit(parameter.charAt(i - 1))));
                    if (unaryPlus) {
                        resultExpression.addExpressionItem(new Operator(ArithmeticOperation.UNARY_PLUS));
                    } else {
                        resultExpression.addExpressionItem(new Operator(ArithmeticOperation.BINARY_PLUS));
                    }
                }
                break;
                case '/':
                    resultExpression.addExpressionItem(new Operator(ArithmeticOperation.DIVIDE));
                    break;
                case 'x':
                    resultExpression.addExpressionItem(new Operator(ArithmeticOperation.MULTIPLY));
                    break;
                case '$': {
                    StringBuilder varIndexBuilder = new StringBuilder();
                    int varIndexParseIndex = i + 1;
                    while (Character.isDigit(parameter.charAt(varIndexParseIndex))) {
                        varIndexBuilder.append(parameter.charAt(varIndexParseIndex));
                        varIndexParseIndex++;
                        if (varIndexParseIndex == parameter.length()) {
                            break;
                        }
                    }
                    i = varIndexParseIndex - 1;
                    int varIndex = Integer.parseInt(varIndexBuilder.toString());
                    resultExpression.addExpressionItem(new Variable(varIndex));
                }
                break;
                default: {
                    StringBuilder constantBuilder = new StringBuilder();
                    int constanParseIndex = i;
                    while (Character.isDigit(parameter.charAt(constanParseIndex)) || parameter.charAt(constanParseIndex) == '.') {
                        constantBuilder.append(parameter.charAt(constanParseIndex));
                        constanParseIndex++;
                        if (constanParseIndex == (parameter.length())) {
                            break;
                        }
                    }
                    i = constanParseIndex - 1;
                    double constant = Double.parseDouble(constantBuilder.toString());
                    resultExpression.addExpressionItem(new ConstantNumber(constant));
                }
                break;
            }
            i++;
        }
        log.trace(resultExpression);
        return resultExpression;
    }

    /**
     * Command with D-code creating
     * @param currentString current string from file
     * @return GerberCommand
     */
    private GerberCommand createDCode(String currentString) {
        Matcher matcher = DNN_PATTERN.matcher(currentString);
        if (matcher.find()) {
            log.trace("Dnn regexp find");
            int apertureNumber = Integer.parseInt(matcher.group(2));
            log.trace("Aperture number=" + apertureNumber);
            return new DnnCommand(apertureNumber, lineIndex);
        } else {
            return createD01D02D03(currentString);
        }
    }

    /**
     * D01/D02/D03 command creating
     * @param currentString current string from file
     * @return GerberCommand
     */
    private GerberCommand createD01D02D03(String currentString) {
        Matcher matcher = COORDINATE_DATA.matcher(currentString);
        log.trace("Create D01D02D03: ");
        String coordinateName;
        String coordinateStringVal;
        List<Coordinate> coordinateList = new ArrayList<>();
        while (matcher.find()) {
            coordinateName = matcher.group(1);
            coordinateStringVal = matcher.group(2);
            log.trace("Coordinate: " + coordinateName +
                    " value=" + coordinateStringVal);
            coordinateList.add(parseCoordinate(coordinateName, coordinateStringVal));
        }

        if (currentString.contains("D01")) {
            deprecatedCurrentDCommand = GerberCommandName.D01;
            return new D01Command(coordinateList, lineIndex);
        } else if (currentString.contains("D02")) {
            deprecatedCurrentDCommand = GerberCommandName.D02;
            return new D02Command(coordinateList, lineIndex);
        } else if (currentString.contains("D03")) {
            deprecatedCurrentDCommand = GerberCommandName.D03;
            return new D03Command(coordinateList, lineIndex);
        } else {
            log.warn("Coordinate data without D-code detected. String number=" + lineIndex);
            switch (deprecatedCurrentDCommand) {
                case D01:
                    return new D01Command(coordinateList, lineIndex);
                case D02:
                    return new D02Command(coordinateList, lineIndex);
                case D03:
                    return new D03Command(coordinateList, lineIndex);
                default:
                    return null;
            }
        }
    }

    /**
     * Creates coordinate from coordinate name and value
     * @param coordinateName X|Y|I|J
     * @param coordinateStringVal string value of coordinate
     * @return Coordinate
     * @throws IllegalArgumentException when coordinate type is wrong or undefined
     */
    private Coordinate parseCoordinate(String coordinateName, String coordinateStringVal) throws IllegalArgumentException {
        CoordinateType coordinateType;
        switch (coordinateName) {
            case "X":
                coordinateType = CoordinateType.X;
                break;
            case "Y":
                coordinateType = CoordinateType.Y;
                break;
            case "I":
                coordinateType = CoordinateType.I;
                break;
            case "J":
                coordinateType = CoordinateType.J;
                break;
            default:
                throw new IllegalArgumentException("WRONG COORDINATE TYPE! String number=" + lineIndex);
        }

        StringBuilder valBuilder = new StringBuilder(coordinateStringVal);
        int sign = 0; //count of positions for sign
        if ((valBuilder.charAt(0) == '-') || (valBuilder.charAt(0) == '+')) {
            sign = 1;
        }
        int countOfDigitByFormat = coordinateFormat.getDecCount() + coordinateFormat.getIntCount();
        int actualAndFormatCountDifference = countOfDigitByFormat - (coordinateStringVal.length() - sign);
        if (actualAndFormatCountDifference > 0) {
            for (int i = 0; i < actualAndFormatCountDifference; i++) {
                valBuilder.insert(sign, '0');
            }
        }

        valBuilder.insert(sign + coordinateFormat.getIntCount(), '.');
        log.trace("Value string after trimming: " + valBuilder);
        double value = Double.parseDouble(valBuilder.toString());
        return new Coordinate(value, coordinateType);
    }

    /**
     * Looking for G-code (G01/G02/G03) in current string with operation commands.
     * Used for supporting deprectaed practices
     * @param currentString current string from file
     */
    private void checkDeprecatedGCodeInOperation(String currentString) {
        if (currentString.contains("G01")) {
            gerberCommands.add(createG01());
        } else if (currentString.contains("G02")) {
            gerberCommands.add(createG02());
        } else if (currentString.contains("G03")) {
            gerberCommands.add(createG03());
        }
    }

    private G01Command createG01() {
        return new G01Command(lineIndex);
    }

    private G02Command createG02() {
        return new G02Command(lineIndex);
    }

    private G03Command createG03() {
        return new G03Command(lineIndex);
    }

    private G74Command createG74() {
        return new G74Command(lineIndex);
    }

    private G75Command createG75() {
        return new G75Command(lineIndex);
    }

    private G36Command createG36() {
        return new G36Command(lineIndex);
    }

    private G37Command createG37() {
        return new G37Command(lineIndex);
    }

    private LPCommand createLP(String currentString) throws IllegalStateException {
        Matcher matcher = LP_PATTERN.matcher(currentString);
        log.trace("LP match regexp find: " + matcher.find());
        String polarityStr = matcher.group(1);
        log.trace("Polarity=" + polarityStr);

        Polarity polarity;
        if (polarityStr.equals("D")) {
            polarity = Polarity.DARK;
        } else if (polarityStr.equals("C")) {
            polarity = Polarity.CLEAR;
        } else {
            throw new IllegalStateException("UNKNOWN POLARITY!");
        }
        return new LPCommand(polarity, lineIndex);
    }

    private LMCommand createLM(String currentString) throws IllegalStateException {
        Matcher matcher = LM_PATTERN.matcher(currentString);
        log.trace("LM match regexp find: " + matcher.find());
        String mirroringStr = matcher.group(1);
        log.trace("Mirroring=" + mirroringStr);

        Mirroring mirroring;
        switch (mirroringStr) {
            case "X":
                mirroring = Mirroring.MIRRORING_BY_X;
                break;
            case "Y":
                mirroring = Mirroring.MIRRORING_BY_Y;
                break;
            case "XY":
                mirroring = Mirroring.MIRRORING_BY_XY;
                break;
            case "N":
                mirroring = Mirroring.NO_MIRRORING;
                break;
            default:
                throw new IllegalStateException("UNKNOWN MIRRORING!");
        }
        return new LMCommand(mirroring, lineIndex);
    }

    private LRCommand createLR(String currentString) throws IllegalStateException {
        Matcher matcher = LR_PATTERN.matcher(currentString);
        log.trace("LR match regexp find: " + matcher.find());
        String rotationStr = matcher.group(1);
        log.trace("Rotation=" + rotationStr);

        double rotation;
        try {
            rotation = Double.parseDouble(rotationStr);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("ILLEGAL ROTATION!");
        }
        return new LRCommand(rotation, lineIndex);
    }

    private LSCommand createLS(String currentString) throws IllegalStateException {
        Matcher matcher = LS_PATTERN.matcher(currentString);
        log.trace("LS match regexp find: " + matcher.find());
        String scalingStr = matcher.group(1);
        log.trace("Scaling=" + scalingStr);

        double scaling;
        try {
            scaling = Double.parseDouble(scalingStr);
            return new LSCommand(scaling, lineIndex);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("ILLEGAL SCALING!");
        }
    }

    private TFCommand createTF() {
        //TODO
        return null;
    }

    private TACommand createTA() {
        //TODO
        return null;
    }

    private TOCommand createTO() {
        //TODO
        return null;
    }

    private TDCommand createTD() {
        //TODO
        return null;
    }

    /**
     * Creates opening or closing SR command
     * @param currentString current string from file
     * @return SROpenCommand or SRCloseCommand
     */
    private GerberCommand createSR(String currentString) {
        if (currentString.contains("%SR*%")) {
            return new SRCloseCommand(lineIndex);
        } else {
            Matcher matcher = SR_PARAMETERS.matcher(currentString);
            String parameterName;
            String parameterStringVal;
            int X = 1;
            int Y = 1;
            double I = 0.0;
            double J = 0.0;
            while (matcher.find()) {
                parameterName = matcher.group(1);
                parameterStringVal = matcher.group(2);
                switch (parameterName) {
                    case "X":
                        X = Integer.parseInt(parameterStringVal);
                        break;
                    case "Y":
                        Y = Integer.parseInt(parameterStringVal);
                        break;
                    case "I":
                        I = Double.parseDouble(parameterStringVal);
                        break;
                    case "J":
                        J = Double.parseDouble(parameterStringVal);
                        break;
                }
            }
            return new SROpenCommand(X, Y, I, J, lineIndex);
        }
    }

    /**
     * Creates opening or closing AB command
     * @param currentString current string from file
     * @return ABOpenCommand or ABCloseCommand
     */
    private GerberCommand createAB(String currentString) {
        Matcher matcher = AB_PATTERN.matcher(currentString);
        log.trace("AB match regexp find: " + matcher.find());
        if (matcher.group(1) != null) {
            String apertureIDStr = matcher.group(1);
            int apertureID = Integer.parseInt(apertureIDStr);
            return new ABOpenCommand(apertureID, lineIndex);
        }
        return new ABCloseCommand(lineIndex);
    }

    /**
     * Stores the coordinate data format of the gerber file after FS command parsing
     */
    private class FormatSpecification {
        private final int integerDigitCount;
        private final int decimalDigitCount;

        public FormatSpecification(int integerDigitCount, int decimalDigitCount) {
            this.integerDigitCount = integerDigitCount;
            this.decimalDigitCount = decimalDigitCount;
        }

        public int getIntCount() {
            return integerDigitCount;
        }

        public int getDecCount() {
            return decimalDigitCount;
        }
    }
}
