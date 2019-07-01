/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.expressions;

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
public interface ExpressionVisitor
{

    void visit(Object o);

    void visitExpression(Expression o);

    void visitExpressionString(ExpressionString o);

    void visitString(String o);

    void visitBinaryOperatorExpression(BinaryOperatorExpression o);

    void visitBinaryOperator(BinaryOperator binaryOperator);

    void visitFunctionInvocation(FunctionInvocation functionInvocation);

    void visitComplexValueExpression(ComplexValue complexValue);

    void visitConditionalExpression(ConditionalExpression conditionalExpression);

    void visitLiteralExpression(Literal literal);

    void visitNamedValue(NamedValue namedValue);

    void visitBinaryOperatorExpressionStart(BinaryOperatorExpression binaryOperatorExpression);

    void visitBinaryOperatorSubExpression(Expression expression, ExpressionVisitor v);

    void visitBinaryOperatorExpressionEnd(BinaryOperatorExpression binaryOperatorExpression);

    void visitFunctionInvocationStart(FunctionInvocation functionInvocation);

    void visitFunctionInvocationEnd(FunctionInvocation functionInvocation);

    List<Expression> getFunctionArgumentList(FunctionInvocation functionInvocation);

    List<Pair<? extends BinaryOperator, ? extends Expression>> getBinaryOperatorOperatorExpressionPairs(BinaryOperatorExpression binaryOperatorExpression);

    String getConvertsMethodName();

    void visitParamDivider(Expression e, ExpressionVisitor v);

    void visitUnaryOperator(UnaryOperatorExpression unaryOperatorExpression);

}
