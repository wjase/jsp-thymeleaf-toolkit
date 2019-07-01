/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.expressions;

import com.cybernostics.jsp2thymeleaf.api.elements.ScopedJSPConverters;
import com.cybernostics.jsp2thymeleaf.api.expressions.function.FunctionConverterSource;
import com.cybernostics.jsp2thymeleaf.api.expressions.HasWriter;
import com.cybernostics.jsp2thymeleaf.api.expressions.function.SymbolWriter;
import com.cybernostics.jsp2thymeleaf.api.util.PrefixedName;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Optional;
import org.apache.commons.el.BinaryOperator;
import org.apache.commons.el.BinaryOperatorExpression;
import org.apache.commons.el.ComplexValue;
import org.apache.commons.el.Expression;
import org.apache.commons.el.FunctionInvocation;
import org.apache.commons.el.Literal;
import org.apache.commons.el.NamedValue;
import org.apache.commons.el.UnaryOperatorExpression;

/**
 *
 * @author jason
 */
public class ExpressionWritingVisitor extends DefaultExpressionVisitor
{

    private final Writer w;
    private final ScopedJSPConverters converters;

    public ExpressionWritingVisitor(Writer w, ScopedJSPConverters converters)
    {
        this.w = w;
        this.converters = converters;
    }

    @Override
    public void visitBinaryOperatorExpressionEnd(BinaryOperatorExpression binaryOperatorExpression)
    {
        //append(w, binaryOperatorExpression.getExpressionString());
    }

    @Override
    public void visitBinaryOperatorSubExpression(Expression expression, ExpressionVisitor v)
    {
        append(w, expression.getExpressionString());
    }

    @Override
    public void visitBinaryOperatorExpressionStart(BinaryOperatorExpression binaryOperatorExpression)
    {
        //append(w, binaryOperatorExpression.getExpressionString());
    }

    @Override
    public void visitLiteralExpression(Literal literal)
    {
        append(w, literal.getExpressionString());
    }

    @Override
    public void visitBinaryOperator(BinaryOperator binaryOperator)
    {
        SymbolWriter.write(w, binaryOperator.getOperatorSymbol());
    }

    @Override
    public void visitUnaryOperator(UnaryOperatorExpression unaryOperatorExpression)
    {
        SymbolWriter.write(w, unaryOperatorExpression.getOperator().getOperatorSymbol());
    }

    @Override
    public void visitComplexValueExpression(ComplexValue complexValue)
    {
        append(w, complexValue.getExpressionString());
    }

    @Override
    public void visitParamDivider(Expression e, ExpressionVisitor v)
    {
        append(w, ",");
    }

    @Override
    public void visitNamedValue(NamedValue namedValue)
    {
        append(w, namedValue.getExpressionString());
    }

    @Override
    public void visitString(String o)
    {
        append(w, o);
    }

    @Override
    public void visitExpression(Expression o)
    {
        append(w, o.getExpressionString());
    }

    void append(Writer w, String toAppend)
    {
        try
        {
            w.append(toAppend);
        } catch (IOException ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void visitFunctionInvocationStart(FunctionInvocation functionInvocation)
    {
        final PrefixedName prefixedNameFor = PrefixedName.prefixedNameFor(functionInvocation.getFunctionName());
        functionConverterForPrefix = converters.functionConverterForPrefix(prefixedNameFor.getPrefix());
        converterFor = functionConverterForPrefix.get().converterFor(prefixedNameFor);
        final ExpressionVisitor converter = converterFor.get();
        if (converter instanceof HasWriter)
        {
            ((HasWriter) converter).setWriter(w);
        }
        converter.visitFunctionInvocationStart(functionInvocation);
    }
    private Optional<ExpressionVisitor> converterFor;
    private Optional<FunctionConverterSource> functionConverterForPrefix;

    @Override
    public void visitFunctionInvocationEnd(FunctionInvocation functionInvocation)
    {
        converterFor.get().visitFunctionInvocationEnd(functionInvocation);
    }

    @Override
    public List<Expression> getFunctionArgumentList(FunctionInvocation functionInvocation)
    {
        return converterFor.get().getFunctionArgumentList(functionInvocation);
    }

}
