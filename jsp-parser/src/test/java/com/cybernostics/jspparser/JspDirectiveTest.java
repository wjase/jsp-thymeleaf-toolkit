/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jspparser;

import com.cybernostics.jsp.parser.JSPLexer;
import com.cybernostics.jsp.parser.JSPParser;
import java.util.function.Function;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 *
 * @author jason
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class JspDirectiveTest
{

    @Test
    public void shouldParseJspDirective()
    {
        String input = "<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %>";
        parse(input, (parser) -> parser.jspDirective());
    }

    private static ParserRuleContext parse(String inputText, Function<JSPParser, ParserRuleContext> fn)
    {
        // Get our lexer
        JSPLexer lexer = new JSPLexer(new ANTLRInputStream(inputText));

        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        tokens.consume();
        assertThat(tokens.getTokens().size(), Matchers.greaterThan(0));
        tokens.reset();

        // Pass the tokens to the parser
        JSPParser parser = new JSPParser(tokens);

        // Specify our entry point
        return fn.apply(parser);

    }

}
