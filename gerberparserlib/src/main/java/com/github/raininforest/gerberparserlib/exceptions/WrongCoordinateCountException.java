package com.github.raininforest.gerberparserlib.exceptions;

import androidx.annotation.Nullable;

public class WrongCoordinateCountException extends IllegalArgumentException{
    @Nullable
    @Override
    public String getMessage() {
        return "Wrong coordinates count in D01/D02/D03 command!";
    }
}
