/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.elements;

import com.cybernostics.jsp2thymeleaf.api.common.TokenisedFile;
import com.cybernostics.jsp2thymeleaf.api.exception.DefaultFileErrorLocation;
import com.cybernostics.jsp2thymeleaf.api.exception.FileErrorLocation;
import com.cybernostics.jsp2thymeleaf.api.exception.JSP2ThymeLeafException;
import com.cybernostics.jsp2thymeleaf.api.exception.MutableFileLocation;
import org.apache.commons.el.parser.ParseException;

/**
 *
 * @author jason
 */
public class JSP2ThymeleafExpressionParseException extends JSP2ThymeLeafException implements MutableFileLocation
{

    private String filename = "unknown";
    private int line;
    private int column;

    public JSP2ThymeleafExpressionParseException(ParseException exception, int line, int column)
    {
        super(exception);
        this.line = exception.currentToken.beginLine + line - 1;
        this.column = exception.currentToken.beginColumn + column;

    }

    @Override
    public FileErrorLocation getLocation()
    {
        return new DefaultFileErrorLocation(filename, line, column);
    }

    @Override
    public void setFile(TokenisedFile file)
    {
        filename = file.getFilePath().toString();
    }

    @Override
    public String getMessage()
    {
        String message = super.getMessage();
        message = message.replaceAll("at line \\d+, column \\d+", "");
        message = message.replaceAll("org.apache.commons.el.parser.ParseException:",
                getClass().getCanonicalName() + " " + getLocation());

        return message;
    }

}
