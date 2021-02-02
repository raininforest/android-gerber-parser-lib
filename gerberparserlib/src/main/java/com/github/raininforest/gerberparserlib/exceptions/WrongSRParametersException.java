package com.github.raininforest.gerberparserlib.exceptions;

import androidx.annotation.Nullable;

public class WrongSRParametersException extends IllegalArgumentException {
    @Nullable
    @Override
    public String getMessage() {
        return "Wrong SR command parameters!";
    }
}
