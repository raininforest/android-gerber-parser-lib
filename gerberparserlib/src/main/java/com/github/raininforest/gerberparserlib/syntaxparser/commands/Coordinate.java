package com.github.raininforest.gerberparserlib.syntaxparser.commands;

import com.github.raininforest.gerberparserlib.enums.CoordinateType;

public class Coordinate {
    private final double value;
    private final CoordinateType coordinateType;

    public Coordinate(double value, CoordinateType coordinateType) {
        this.value = value;
        this.coordinateType = coordinateType;
    }

    public double getValue() {
        return value;
    }

    public CoordinateType getCoordinateType() {
        return coordinateType;
    }

    @Override
    public String toString() {
        return coordinateType + "=" + value;
    }
}
