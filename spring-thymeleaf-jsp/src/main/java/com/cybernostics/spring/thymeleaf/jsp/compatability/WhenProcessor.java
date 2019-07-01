/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.spring.thymeleaf.jsp.compatability;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.standard.processor.AbstractStandardConditionalVisibilityTagProcessor;
import org.thymeleaf.standard.processor.StandardIfTagProcessor;
import org.thymeleaf.util.EvaluationUtils;

/**
 *
 * @author jason
 */
public class WhenProcessor extends AbstractStandardConditionalVisibilityTagProcessor
{

    public WhenProcessor(StandardIfTagProcessor it, String dialectPrefix)
    {
        super(it.getTemplateMode(), dialectPrefix, "when", it.getPrecedence());
    }

    @Override
    protected boolean isVisible(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue)
    {
        ChooseChildTagProcessor.ChooseChildExecutionState childState = (ChooseChildTagProcessor.ChooseChildExecutionState) context.getVariable(ChooseChildTagProcessor.SWITCH_VARIABLE_NAME);

        if (childState == null || !childState.isExecuted())
        {
            final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(context.getConfiguration());

            final IStandardExpression expression
                    = expressionParser.parseExpression(context, attributeValue);
            final Object value = expression.execute(context);
            final boolean result = EvaluationUtils.evaluateAsBoolean(value);
            if (childState != null)
            {
                childState.setExecuted(result);
            }
            return result;
        }
        return false;

    }

}
