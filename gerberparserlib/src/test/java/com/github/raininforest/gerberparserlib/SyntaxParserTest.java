package com.github.raininforest.gerberparserlib;

import com.github.raininforest.gerberparserlib.syntaxparser.GerberFileReader;
import com.github.raininforest.gerberparserlib.syntaxparser.SyntaxParser;

import org.junit.Assert;
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
            GerberFileReader gerberFileReader = new GerberFileReader(file.getPath());
            SyntaxParser syntaxParser = new SyntaxParser(gerberFileReader.getFilename(),
                    gerberFileReader.getStringList());
            syntaxParser.parse();
            System.out.println(file + " parsed");
        }
    }

    @Test
    public void invalid_gerber_file_test() throws FileNotFoundException {
        GerberFileReader gerberFileReader = new GerberFileReader(
                "src/test/test_gerbers/notValidFile");
        SyntaxParser syntaxParser = new SyntaxParser(gerberFileReader.getFilename(),
                gerberFileReader.getStringList());
        Assert.assertEquals(0, syntaxParser.parse().size());
    }
}