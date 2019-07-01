package com.cybernostics.jsp2thymeleaf.api.elements;
///**
// *

import com.cybernostics.jsp2thymeleaf.api.expressions.ExpressionWalker;
import com.cybernostics.jsp2thymeleaf.api.expressions.ExpressionWritingVisitor;
import java.io.StringWriter;
import org.apache.commons.el.parser.ParseException;
import org.apache.commons.lang3.StringUtils;

// * @author jason
// *
public class ELExpressionConverter
{
//

    public static String convertEmbedded(String toConvert, ScopedJSPConverters converters) throws ParseException
    {
        ExpressionWalker ew = new ExpressionWalker(); //Logger.getLogger(ELExpressionConverter.class.getName()).log(Level.SEVERE, null, ex);
        final StringWriter stringWriter = new StringWriter();
        boolean isExpression = ew.walkExpressionString(toConvert, new ExpressionWritingVisitor(stringWriter, converters));
        String expression = stringWriter.toString();
        if (expression.startsWith("\""))
        {
            expression = StringUtils.strip(expression, "\"");
            return "'" + expression + "'";
        }
        if (expression.startsWith("\'"))
        {
            return expression;
        }
        return isExpression ? "${" + stringWriter.toString() + "}" : stringWriter.toString();

    }

    public static String convert(String toConvert, ScopedJSPConverters converters) throws ParseException
    {
        ExpressionWalker ew = new ExpressionWalker(); //Logger.getLogger(ELExpressionConverter.class.getName()).log(Level.SEVERE, null, ex);
        final StringWriter stringWriter = new StringWriter();
        boolean isExpression = ew.walkExpressionString(toConvert, new ExpressionWritingVisitor(stringWriter, converters));
        return isExpression ? "${" + stringWriter.toString() + "}" : stringWriter.toString();

    }
//
}
