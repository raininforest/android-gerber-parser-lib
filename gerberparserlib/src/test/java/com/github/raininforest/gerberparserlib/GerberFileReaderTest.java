package com.github.raininforest.gerberparserlib;

import com.github.raininforest.gerberparserlib.syntaxparser.GerberFileReader;


import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

public class GerberFileReaderTest {

    @Test(expected = FileNotFoundException.class)
    public void not_existing_file() throws FileNotFoundException {
        GerberFileReader gerberFileReader = new GerberFileReader("___");
        gerberFileReader.getStringList();
    }

    @Test
    public void not_empty_file() throws FileNotFoundException {
        GerberFileReader gerberFileReader = new GerberFileReader("src/test/test_gerbers/gerber1");
        Assert.assertNotEquals(0, gerberFileReader.getStringList().size());
    }
}
