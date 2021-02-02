package com.github.raininforest.gerberparserlib.exceptions;

import androidx.annotation.Nullable;

public class WrongApertureNumberException extends IllegalArgumentException {
    @Nullable
    @Override
    public String getMessage() {
        return "The aperture number must be >= 10!";
    }
}
