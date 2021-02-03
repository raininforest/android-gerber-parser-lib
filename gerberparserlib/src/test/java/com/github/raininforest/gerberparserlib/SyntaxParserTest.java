package com.github.raininforest.gerberparserlib;

import com.github.raininforest.gerberparserlib.syntaxparser.GerberFileReader;
import com.github.raininforest.gerberparserlib.syntaxparser.SyntaxParser;
import com.github.raininforest.gerberparserlib.syntaxparser.commands.GerberCommand;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

// add VM option: -Dlog4j.configurationFile="src/log4j2_config.xml"
// to watch all log levels
public class SyntaxParserTest {
    private SyntaxParser syntaxParser;

    @Before
    public void init() throws FileNotFoundException {
        GerberFileReader gerberFileReader = new GerberFileReader(
                "src/test/test_gerbers/gerber4");
        List<String> stringList = gerberFileReader.getStringList();
        syntaxParser = new SyntaxParser(stringList);
    }

    @Test
    public void parse() {
        final List<GerberCommand> parse = syntaxParser.parse();
    }
}
