package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

import java.util.List;

public class D03Command extends OperationCommand {
    public D03Command(List<Coordinate> coordinateDataList, int stringNumber) {
        super(stringNumber, GerberCommandName.D03);
        MIN_COORDINATE_COUNT = 0;
        MAX_COORDINATE_COUNT = 2;
        this.coordinateDataList = coordinateDataList;
        checkCoordinateCount();
        log.trace("D03 command created");
    }

    @Override
    public String toString() {
        return "D03Command{" + coordinateDataList + '}';
    }
}
