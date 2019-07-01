/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.elements;

import com.cybernostics.jsp.parser.JSPParser;
import java.util.Map;
import java.util.Optional;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;
import org.apache.commons.el.parser.ParseException;

/**
 *
 * @author jason
 */
public class ScopedJspElementNodeContext
{

    public static ScopedJspElementNodeContext nodeContext(JSPParser.JspElementContext elementContext, JSPElementNodeConverter converter)
    {
        return new ScopedJspElementNodeContext(converter, elementContext);
    }

    private JSPElementNodeConverter converter;

    public JSPElementNodeConverter getConverter()
    {
        return converter;
    }

    public JSPParser.JspElementContext getElementContext()
    {
        return elementContext;
    }
    private JSPParser.JspElementContext elementContext;

    private ScopedJspElementNodeContext(JSPElementNodeConverter converter, JSPParser.JspElementContext elementContext)
    {
        this.converter = converter;
        this.elementContext = elementContext;
    }

    public Optional<String> attAsValue(String name)
    {
        return elementContext.atts
                .stream()
                .filter(att -> name.equals(att.name.getText()))
                .map(i -> attributeValue(i.value, converter))
                .findFirst();
    }

    public Optional<String> attAsName(String name)
    {
        return elementContext.atts
                .stream()
                .filter(att -> name.equals(att.name.getText()))
                .map(i -> attributeIdentifier(i.value, converter))
                .findFirst();
    }

    public Stream<ScopedJspElementNodeContext> childElements()
    {
        return elementContext.children
                .stream()
                .filter(c -> c instanceof JSPParser.HtmlContentContext)
                .flatMap(c -> ((JSPParser.HtmlContentContext) c).children.stream())
                .filter(c -> c instanceof JSPParser.JspElementContext)
                .map(c -> nodeContext((JSPParser.JspElementContext) c, converter));
    }

    public Map<String, String> paramsBy(String key, String value)
    {
        return childElements()
                .collect(toMap(n -> n.attAsName(key).orElse(""),
                        n -> n.attAsValue(value).orElse("")));
    }

    private static String attributeValue(JSPParser.HtmlAttributeValueContext context, JSPElementNodeConverter elementNodeConverter)
    {
        final JSPParser.HtmlAttributeValueConstantContext asConstant = context.htmlAttributeValueConstant();
        if (asConstant != null)
        {
            return "'" + asConstant.getText() + "'";
        }
        final JSPParser.HtmlAttributeValueExprContext asExpression = context.htmlAttributeValueExpr();
        if (asExpression != null)
        {
            try
            {
                return ELExpressionConverter.convertEmbedded(asExpression.getText(), elementNodeConverter.getScopedConverters());
            } catch (ParseException ex)
            {
                throw new RuntimeException(ex);
            }
        }
        return "unknown expression type";//elementNodeConverter.processAsAttributeValue(context.jspQuotedElement(), elementNodeConverter);
    }

    private static String attributeIdentifier(JSPParser.HtmlAttributeValueContext context, JSPElementNodeConverter elementNodeConverter)
    {
        final JSPParser.HtmlAttributeValueConstantContext asConstant = context.htmlAttributeValueConstant();
        if (asConstant != null)
        {
            return asConstant.getText();
        }
        final JSPParser.HtmlAttributeValueExprContext asExpression = context.htmlAttributeValueExpr();
        if (asExpression != null)
        {
            try
            {
                return ELExpressionConverter.convertEmbedded(asExpression.getText(), elementNodeConverter.getScopedConverters());
            } catch (ParseException ex)
            {
                throw new RuntimeException(ex);
            }
        }
        return "unknown expression type";//elementNodeConverter.processAsAttributeValue(context.jspElement(), elementNodeConverter);
    }

}
