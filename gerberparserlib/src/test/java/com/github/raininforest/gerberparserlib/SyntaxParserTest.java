package com.github.raininforest.gerberparserlib;

import com.github.raininforest.gerberparserlib.syntaxparser.GerberFileReader;
import com.github.raininforest.gerberparserlib.syntaxparser.SyntaxParser;

import org.apache.logging.log4j.core.util.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

// add VM option: -Dlog4j.configurationFile="src/log4j2_config.xml"
// to watch all log levels
public class SyntaxParserTest {

    @Test
    public void valid_gerber_file_test() throws FileNotFoundException {
        File testGerberDir = new File("src/test/test_gerbers");
        File[] files = testGerberDir.listFiles();
        for (File file: files) {
            GerberFileReader gerberFileReader = new GerberFileReader(file.getAbsolutePath());
            SyntaxParser syntaxParser = new SyntaxParser(gerberFileReader.getStringList());
            syntaxParser.parse();
        }
    }

    @Test
    public void invalid_gerber_file_test() throws FileNotFoundException {
        GerberFileReader gerberFileReader = new GerberFileReader(
                "src/test/test_gerbers/notValidFile");
        SyntaxParser syntaxParser = new SyntaxParser(gerberFileReader.getStringList());
        Assert.isEmpty(syntaxParser.parse());
    }
}