package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;
import com.github.raininforest.gerberparserlib.exceptions.WrongCoordinateCountException;

import java.util.List;

/**
 * Abstract operation command D01/D02/D03 with coordinate data
 */
public abstract class OperationCommand extends GerberCommand{
    protected List<Coordinate> coordinateDataList;
    protected int MIN_COORDINATE_COUNT;
    protected int MAX_COORDINATE_COUNT;

    public OperationCommand(int stringNumber, GerberCommandName gerberCommandName) {
        super(stringNumber, gerberCommandName);
    }

    protected void checkCoordinateCount() throws WrongCoordinateCountException {
        if ((coordinateDataList.size() > MAX_COORDINATE_COUNT) || (coordinateDataList.size() < MIN_COORDINATE_COUNT)){
            log.error("WrongCoordinateCountException. String number=" + this.stringNumber);
            throw new WrongCoordinateCountException();
        }
    }
}
