/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.expressions;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.el.BinaryOperator;
import org.apache.commons.el.BinaryOperatorExpression;
import org.apache.commons.el.ComplexValue;
import org.apache.commons.el.ConditionalExpression;
import org.apache.commons.el.Expression;
import org.apache.commons.el.ExpressionString;
import org.apache.commons.el.FunctionInvocation;
import org.apache.commons.el.Literal;
import org.apache.commons.el.NamedValue;
import org.apache.commons.el.UnaryOperatorExpression;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author jason
 */
public class DefaultExpressionVisitor implements ExpressionVisitor
{

    @Override
    public void visit(Object o)
    {
    }

    @Override
    public void visitExpression(Expression o)
    {
    }

    @Override
    public void visitExpressionString(ExpressionString o)
    {
    }

    @Override
    public void visitString(String o)
    {
    }

    @Override
    public void visitBinaryOperatorExpression(BinaryOperatorExpression o)
    {
    }

    @Override
    public void visitBinaryOperator(BinaryOperator binaryOperator)
    {
    }

    @Override
    public void visitFunctionInvocation(FunctionInvocation functionInvocation)
    {
    }

    @Override
    public void visitComplexValueExpression(ComplexValue complexValue)
    {
    }

    @Override
    public void visitConditionalExpression(ConditionalExpression conditionalExpression)
    {
    }

    @Override
    public void visitLiteralExpression(Literal literal)
    {
    }

    @Override
    public void visitNamedValue(NamedValue namedValue)
    {
    }

    @Override
    public void visitUnaryOperator(UnaryOperatorExpression unaryOperatorExpression)
    {
    }

    @Override
    public void visitBinaryOperatorExpressionStart(BinaryOperatorExpression binaryOperatorExpression)
    {
    }

    @Override
    public void visitBinaryOperatorSubExpression(Expression expression, ExpressionVisitor v)
    {
    }

    @Override
    public void visitBinaryOperatorExpressionEnd(BinaryOperatorExpression binaryOperatorExpression)
    {
    }

    @Override
    public void visitFunctionInvocationStart(FunctionInvocation functionInvocation)
    {
    }

    @Override
    public void visitFunctionInvocationEnd(FunctionInvocation functionInvocation)
    {
    }

    @Override
    public List<Expression> getFunctionArgumentList(FunctionInvocation functionInvocation)
    {
        return functionInvocation.getArgumentList();
    }

    @Override
    public List<Pair<? extends BinaryOperator, ? extends Expression>> getBinaryOperatorOperatorExpressionPairs(BinaryOperatorExpression binaryOperatorExpression)
    {
        final List operators = binaryOperatorExpression.getOperators();
        final List expressions = binaryOperatorExpression.getExpressions();
        List<Pair<? extends BinaryOperator, ? extends Expression>> pairs = new ArrayList<>();
        for (int i = 0; i < operators.size(); i++)
        {
            pairs.add(Pair.of((BinaryOperator) operators.get(i), (Expression) expressions.get(i)));
        }
        return pairs;
    }

    @Override
    public void visitParamDivider(Expression e, ExpressionVisitor v)
    {
    }

    public String getConvertsMethodName()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
