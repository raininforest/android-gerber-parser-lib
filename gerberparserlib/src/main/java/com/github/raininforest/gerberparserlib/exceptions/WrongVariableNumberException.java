package com.github.raininforest.gerberparserlib.exceptions;

import androidx.annotation.Nullable;

public class WrongVariableNumberException extends IllegalArgumentException{
    @Nullable
    @Override
    public String getMessage() {
        return "Variable number must be > 0!";
    }
}
