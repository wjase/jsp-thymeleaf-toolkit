/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp.parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author jason
 */
public class JSPParserRunner
{

    private static void printJSP(String inputText)
    {
        // Get our lexer
        JSPLexer lexer = new JSPLexer(new ANTLRInputStream(inputText));

        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        JSPParser parser = new JSPParser(tokens);

        // Specify our entry point
        JSPParser.JspDocumentContext documentContext = parser.jspDocument();

        // Walk it and attach our listener
        ParseTreeWalker walker = new ParseTreeWalker();
        AntlrJSPListener listener = new AntlrJSPListener(parser);
        walker.walk(listener, documentContext);
    }

    public static void main(String[] args)
    {
        final URL resource = JSPParserRunner.class.getClassLoader().getResource("01_clean_html.html");
        ;

        try
        {
            try
            {
                printJSP(new String(Files.readAllBytes(Paths.get(resource.toURI()))));
            } catch (IOException ex)
            {
                Logger.getLogger(JSPParserRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (URISyntaxException ex)
        {
            Logger.getLogger(JSPParserRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
