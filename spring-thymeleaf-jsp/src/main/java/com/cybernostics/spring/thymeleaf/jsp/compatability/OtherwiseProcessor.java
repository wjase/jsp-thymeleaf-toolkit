/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.spring.thymeleaf.jsp.compatability;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.standard.processor.AbstractStandardConditionalVisibilityTagProcessor;
import org.thymeleaf.standard.processor.StandardIfTagProcessor;

/**
 *
 * @author jason
 */
public class OtherwiseProcessor extends AbstractStandardConditionalVisibilityTagProcessor
{

    public OtherwiseProcessor(StandardIfTagProcessor it, String prefix)
    {
        super(it.getTemplateMode(), prefix, "otherwise", it.getPrecedence());
    }

    @Override
    protected boolean isVisible(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue)
    {
        ChooseChildTagProcessor.ChooseChildExecutionState childState = (ChooseChildTagProcessor.ChooseChildExecutionState) context.getVariable(ChooseChildTagProcessor.SWITCH_VARIABLE_NAME);

        if (childState == null || !childState.isExecuted())
        {
            childState.setExecuted(true);
            return true;

        }
        return false;

    }

}
