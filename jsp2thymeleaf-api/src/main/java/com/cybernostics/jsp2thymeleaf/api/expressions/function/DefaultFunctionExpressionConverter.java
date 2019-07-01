/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.expressions.function;

import com.cybernostics.jsp2thymeleaf.api.expressions.HasWriter;
import static com.cybernostics.jsp2thymeleaf.api.expressions.function.ParameterListConverter.noParamChange;
import com.cybernostics.jsp2thymeleaf.api.expressions.DefaultExpressionVisitor;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.apache.commons.el.Expression;
import org.apache.commons.el.FunctionInvocation;

/**
 *
 * @author jason
 */
public class DefaultFunctionExpressionConverter extends DefaultExpressionVisitor implements HasWriter
{

    private Writer writer;

    @Override
    public void setWriter(Writer writer)
    {
        this.writer = writer;
    }
    private ParameterListConverter paramConverter;

    public static DefaultFunctionExpressionConverter convertsMethodCall(String name)
    {
        return new DefaultFunctionExpressionConverter(name);
    }

    public DefaultFunctionExpressionConverter toMethodCall(String newName)
    {
        this.newFunctionName = newName;
        return this;
    }

    public DefaultFunctionExpressionConverter withNewParams(ParameterListConverter listconverter)
    {
        this.paramConverter = listconverter;
        return this;
    }

    private String oldFunctionName;
    private String newFunctionName;

    protected DefaultFunctionExpressionConverter(String name)
    {
        oldFunctionName = name;
        newFunctionName = name;
        paramConverter = noParamChange();
    }

    @Override
    public List<Expression> getFunctionArgumentList(FunctionInvocation functionInvocation)
    {
        return paramConverter.convertParams(super.getFunctionArgumentList(functionInvocation));
    }

    @Override
    public void visitFunctionInvocationEnd(FunctionInvocation functionInvocation)
    {
        append(")");
    }

    @Override
    public void visitFunctionInvocationStart(FunctionInvocation functionInvocation)
    {
        append(newFunctionName);
        append("(");
    }

    private void append(String toAppend)
    {
        try
        {
            writer.write(toAppend);
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public String getConvertsMethodName()
    {
        return oldFunctionName;
    }

}
