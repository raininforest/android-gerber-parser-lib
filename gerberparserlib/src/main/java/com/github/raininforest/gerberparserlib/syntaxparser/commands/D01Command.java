package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.GerberCommandName;

import java.util.List;

public class D01Command extends OperationCommand {
    public D01Command(List<Coordinate> coordinateDataList, int stringNumber) {
        super(stringNumber, GerberCommandName.D01);
        MIN_COORDINATE_COUNT = 1;
        MAX_COORDINATE_COUNT = 4;
        this.coordinateDataList = coordinateDataList;
        checkCoordinateCount();
        log.trace("D01 command created");
    }

    @Override
    public String toString() {
        return "D01Command{" + coordinateDataList + '}';
    }
}
