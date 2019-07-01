/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.spring.thymeleaf.jsp.compatability;

/*
 * =============================================================================
 *
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

/**
 *
 * @author Daniel Fern&aacute;ndez
 *
 * @since 3.0.0
 *
 */
public final class ChooseChildTagProcessor extends AbstractAttributeTagProcessor
{

    public static final int PRECEDENCE = 250;
    public static final String ATTR_NAME = "choose";

    public static final String SWITCH_VARIABLE_NAME = "%%FILTER_CHILD_EXPR%%";

    public ChooseChildTagProcessor(final TemplateMode templateMode, final String dialectPrefix)
    {
        super(templateMode, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
    }

    @Override
    protected void doProcess(
            final ITemplateContext context, final IProcessableElementTag tag,
            final AttributeName attributeName, final String attributeValue,
            final IElementTagStructureHandler structureHandler)
    {

//        final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(context.getConfiguration());

        structureHandler.setLocalVariable(SWITCH_VARIABLE_NAME, new ChooseChildExecutionState(attributeValue));

    }

    public static final class ChooseChildExecutionState
    {

        private final String type;
        private boolean executed;

        public ChooseChildExecutionState(final String type)
        {
            super();
            this.type = type;
            this.executed = false;
        }

        public boolean isExecuted()
        {
            return this.executed;
        }

        public void setExecuted(final boolean executed)
        {
            this.executed = executed;
        }

    }

}
