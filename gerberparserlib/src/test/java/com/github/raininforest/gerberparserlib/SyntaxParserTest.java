package com.github.raininforest.gerberparserlib;

import com.github.raininforest.gerberparserlib.syntaxparser.GerberFileReader;
import com.github.raininforest.gerberparserlib.syntaxparser.SyntaxParser;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.GerberCommand;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

public class SyntaxParserTest {
    private SyntaxParser syntaxParser;

    @Before
    public void init() throws FileNotFoundException {
        GerberFileReader gerberFileReader = new GerberFileReader(
                "src/test/test_gerbers/gerber_example.top");
        List<String> stringList = gerberFileReader.getStringList();
        syntaxParser = new SyntaxParser(stringList);
    }

    @Test
    public void parse() {
        final List<GerberCommand> parse = syntaxParser.parse();
    }
}
