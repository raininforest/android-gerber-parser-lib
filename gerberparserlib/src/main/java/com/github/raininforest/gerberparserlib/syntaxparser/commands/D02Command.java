package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

import java.util.List;

public class D02Command extends OperationCommand {
    public D02Command(List<Coordinate> coordinateDataList, int stringNumber) {
        super(stringNumber, GerberCommandName.D02);
        MIN_COORDINATE_COUNT = 0;
        MAX_COORDINATE_COUNT = 2;
        this.coordinateDataList = coordinateDataList;
        checkCoordinateCount();
        log.trace("D02 command created");
    }

    @Override
    public String toString() {
        return "D02Command{" + coordinateDataList + '}';
    }
}
