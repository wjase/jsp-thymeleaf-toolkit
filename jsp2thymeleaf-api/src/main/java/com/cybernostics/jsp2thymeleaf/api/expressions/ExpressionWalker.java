/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.expressions;

import java.io.StringReader;
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
import org.apache.commons.el.parser.ELParser;
import org.apache.commons.el.parser.ParseException;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author jason
 */
public class ExpressionWalker
{

    public boolean walkExpressionString(String toWalk, ExpressionVisitor v) throws ParseException
    {
        ELParser eLParser = new ELParser(new StringReader(toWalk));
        final Object expr = eLParser.ExpressionString();
        if (expr instanceof String)
        {
            v.visitString((String) expr);
            return false;
        }
        walkExpression(expr, v);

        return true;
    }

    public void walkExpression(Object expr, ExpressionVisitor v)
    {
        v.visit(expr);
        if (expr instanceof String)
        {
            v.visitString(expr.toString());
        } else if (expr instanceof ExpressionString)
        {
            ExpressionString es = (ExpressionString) expr;
            for (Object eachSubExpr : es.getElements())
            {
                walkExpression(eachSubExpr, v);
            }
        } else
        {
            walk((Expression) expr, v);
        }
    }

    public void walk(Expression e, ExpressionVisitor v)
    {
        if (e instanceof BinaryOperatorExpression)
        {
            walkBinaryOperatorExpression((BinaryOperatorExpression) e, v);
        } else if (e instanceof FunctionInvocation)
        {
            walkFunctionInvocation((FunctionInvocation) e, v);

        } else if (e instanceof ComplexValue)
        {
            walkComplexValue((ComplexValue) e, v);

        } else if (e instanceof ConditionalExpression)
        {
            walkConditionalExpression((ConditionalExpression) e, v);

        } else if (e instanceof Literal)
        {
            walkLiteral((Literal) e, v);

        } else if (e instanceof NamedValue)
        {
            walkNamedValue((NamedValue) e, v);

        } else if (e instanceof UnaryOperatorExpression)
        {
            walkUnaryOperatorExpression((UnaryOperatorExpression) e, v);
        }
    }

    private void walkBinaryOperatorExpression(BinaryOperatorExpression binaryOperatorExpression, ExpressionVisitor v)
    {
        v.visitBinaryOperatorExpressionStart(binaryOperatorExpression);
        walkExpression(binaryOperatorExpression.getExpression(), v);
        List<Pair<? extends BinaryOperator, ? extends Expression>> pairs = v.getBinaryOperatorOperatorExpressionPairs(binaryOperatorExpression);
        pairs.forEach(pair ->
        {
            walkBinaryOperator((BinaryOperator) pair.getLeft(), v);
            walkExpression((Expression) pair.getRight(), v);
        });
        v.visitBinaryOperatorExpressionEnd(binaryOperatorExpression);

    }

    private void walkFunctionInvocation(FunctionInvocation functionInvocation, ExpressionVisitor v)
    {
        if (v == null)
        {
            throw new IllegalStateException("Visitor is null.");
        }
        if (functionInvocation == null)
        {
            throw new IllegalStateException("Invocation is null.");
        }
        v.visitFunctionInvocationStart(functionInvocation);
        final List<Expression> functionArgumentList = v.getFunctionArgumentList(functionInvocation);
        if (!functionArgumentList.isEmpty())
        {
            Expression lastExpression = functionArgumentList.remove(functionArgumentList.size() - 1);
            for (Expression e : functionArgumentList)
            {
                walkExpression(e, v);
                v.visitParamDivider(e, v);
            }
            walkExpression(lastExpression, v);
        }

        v.visitFunctionInvocationEnd(functionInvocation);
    }

    private void walkComplexValue(ComplexValue complexValue, ExpressionVisitor v)
    {
        v.visitComplexValueExpression(complexValue);
    }

    private void walkConditionalExpression(ConditionalExpression conditionalExpression, ExpressionVisitor v)
    {
        v.visitConditionalExpression(conditionalExpression);
    }

    private void walkLiteral(Literal literal, ExpressionVisitor v)
    {
        v.visitLiteralExpression(literal);
    }

    private void walkNamedValue(NamedValue namedValue, ExpressionVisitor v)
    {
        v.visitNamedValue(namedValue);
    }

    private void walkUnaryOperatorExpression(UnaryOperatorExpression unaryOperatorExpression, ExpressionVisitor v)
    {
        v.visitUnaryOperator(unaryOperatorExpression);
        walk(unaryOperatorExpression.getExpression(), v);

    }

    private void walkBinaryOperator(BinaryOperator binaryOperator, ExpressionVisitor v)
    {
        v.visitBinaryOperator(binaryOperator);
    }

}
