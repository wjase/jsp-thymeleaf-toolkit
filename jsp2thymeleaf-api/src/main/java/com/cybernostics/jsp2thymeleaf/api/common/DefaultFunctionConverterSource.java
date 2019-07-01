/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.common;

import com.cybernostics.jsp2thymeleaf.api.expressions.DefaultExpressionVisitor;
import com.cybernostics.jsp2thymeleaf.api.expressions.ExpressionVisitor;
import com.cybernostics.jsp2thymeleaf.api.expressions.function.FunctionConverterSource;
import com.cybernostics.jsp2thymeleaf.api.util.PrefixedName;
import java.util.Optional;

/**
 *
 * @author jason
 */
public class DefaultFunctionConverterSource extends FunctionConverterSource
{

    private ExpressionVisitor defaultExpressionVisitor = new DefaultExpressionVisitor();

    public DefaultFunctionConverterSource()
    {
    }

    public Optional<ExpressionVisitor> converterFor(PrefixedName domTag)
    {
        return Optional.of(defaultExpressionVisitor);
    }

}
