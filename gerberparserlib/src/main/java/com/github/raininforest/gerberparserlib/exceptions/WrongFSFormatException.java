package com.github.raininforest.gerberparserlib.exceptions;

import androidx.annotation.Nullable;

public class WrongFSFormatException extends IllegalArgumentException{
    @Nullable
    @Override
    public String getMessage() {
        return "Wrong FS format!";
    }
}
