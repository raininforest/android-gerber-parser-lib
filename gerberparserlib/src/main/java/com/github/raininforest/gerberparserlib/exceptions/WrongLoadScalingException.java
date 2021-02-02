package com.github.raininforest.gerberparserlib.exceptions;

import androidx.annotation.Nullable;

public class WrongLoadScalingException extends IllegalArgumentException{
    @Nullable
    @Override
    public String getMessage() {
        return "Scale factor myst be > 0!";
    }
}
