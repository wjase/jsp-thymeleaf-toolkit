package com.cybernostics.jspparser;

import com.cybernostics.jsp.parser.JSPLexer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static java.util.stream.Stream.empty;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.apache.commons.io.FileUtils;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.not;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author jason
 */
@RunWith(Parameterized.class)
public class JSPLexerHappyCaseTest
{

    private static Logger LOG = Logger.getLogger(JSPLexerHappyCaseTest.class.getName());

    private static void tokenInfo(JSPLexer lexer, Token token)
    {
        final int column = token.getCharPositionInLine();
        final String[] tokenNames = lexer.getTokenNames();
        final int type = token.getType();
        final String tokenName = type > 0 ? tokenNames[type] : "<<UNKNOWN>>";
        System.out.println(String.format("(%d,%d) %s -> %s", token.getLine(), column, tokenName, token.getText()));
    }

    private final File jspFile;

    public JSPLexerHappyCaseTest(String name, File JSPFile)
    {
        this.jspFile = JSPFile;

    }

    @Test
    public void shouldParse()
    {

        try
        {
            String jspContent = FileUtils.readFileToString(jspFile, Charset.defaultCharset());
            printJSPTokens(jspContent);
        } catch (IOException ex)
        {
            Logger.getLogger(JSPLexerHappyCaseTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("exception thrown");
        }
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> data()
    {
        final URL resource = JSPLexerHappyCaseTest.class.getClassLoader().getResource("happy_case_files");
        final File file = new File(resource.getFile());
        return Arrays.asList(file.listFiles())
                .stream()
                .filter(it -> it.getName().contains("jsp"))
                .sorted()
                .map(eachFile -> Arrays.asList((Object) eachFile.getName(), (Object) eachFile).toArray())
                .collect(Collectors.toList());
    }

    private static void printJSPTokens(String inputText)
    {
        // Get our lexer
        JSPLexer lexer = new JSPLexer(new ANTLRInputStream(inputText));
        MatcherAssert.assertThat(lexer.getAllTokens(), not(empty()));
        lexer.reset();

        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        tokens.fill();
        tokens.getTokens().stream().forEach((token) -> tokenInfo(lexer, token));

    }
}
