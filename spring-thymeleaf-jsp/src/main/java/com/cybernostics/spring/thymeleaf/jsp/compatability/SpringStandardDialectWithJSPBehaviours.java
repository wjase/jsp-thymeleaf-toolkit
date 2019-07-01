/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.spring.thymeleaf.jsp.compatability;

import static java.util.Arrays.asList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjects;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;
import org.thymeleaf.standard.expression.IStandardVariableExpression;
import org.thymeleaf.standard.expression.IStandardVariableExpressionEvaluator;
import org.thymeleaf.standard.expression.StandardExpressionExecutionContext;
import org.thymeleaf.standard.processor.StandardIfTagProcessor;

/**
 *
 * @author jason
 */
public class SpringStandardDialectWithJSPBehaviours extends SpringStandardDialect
{

    public SpringStandardDialectWithJSPBehaviours()
    {
    }

    @Override
    public IStandardVariableExpressionEvaluator getVariableExpressionEvaluator()
    {
        final IStandardVariableExpressionEvaluator variableExpressionEvaluator = super.getVariableExpressionEvaluator(); //To change body of generated methods, choose Tools | Templates.
        return (IExpressionContext context, IStandardVariableExpression expression, StandardExpressionExecutionContext expContext) ->
        {
            Map<String, Object> pageMap = (Map<String, Object>) context.getVariable("pageScope");
            Set<String> combinedVariableNames = new HashSet();
            combinedVariableNames.addAll(pageMap.keySet());
            combinedVariableNames.addAll(context.getVariableNames());
            Object evaluated = variableExpressionEvaluator.evaluate(new IExpressionContext()
            {
                @Override
                public IEngineConfiguration getConfiguration()
                {
                    return context.getConfiguration();
                }

                @Override
                public IExpressionObjects getExpressionObjects()
                {
                    return context.getExpressionObjects();
                }

                @Override
                public Locale getLocale()
                {
                    return context.getLocale();
                }

                @Override
                public boolean containsVariable(String name)
                {
                    return combinedVariableNames.contains(name);
                }

                @Override
                public Set<String> getVariableNames()
                {
                    return combinedVariableNames;
                }

                @Override
                public Object getVariable(String name)
                {
                    Object variable = context.getVariable(name);
                    if (variable == null)
                    {
                        variable = pageMap.get(name);
                    }
                    return variable;
                }
            }, expression, expContext);
            return evaluated;
        };
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix)
    {
        return createCNStandardProcessorsSet(dialectPrefix);
    }

    /**
     * <p>
     * Create a the set of CNStandard processors, all of them freshly instanced.
     * </p>
     *
     * @param dialectPrefix the prefix established for the Standard Dialect,
     * needed for initialization
     * @return the set of SpringStandard processors.
     */
    public static Set<IProcessor> createCNStandardProcessorsSet(final String dialectPrefix)
    {
        /*
         * It is important that we create new instances here because, if there are
         * several dialects in the TemplateEngine that extend StandardDialect, they should
         * not be returning the exact same instances for their processors in order
         * to allow specific instances to be directly linked with their owner dialect.
         */

        final Set<IProcessor> standardProcessors = createSpringStandardProcessorsSet(dialectPrefix);

        final Set<IProcessor> processors = standardProcessors
                .stream()
                .flatMap(it -> (it instanceof StandardIfTagProcessor)
                ? asList(new ChooseChildTagProcessor(it.getTemplateMode(), dialectPrefix),
                        new OtherwiseProcessor((StandardIfTagProcessor) it, dialectPrefix),
                        new WhenProcessor((StandardIfTagProcessor) it, dialectPrefix),
                        it).stream()
                : asList(it).stream())
                .collect(toSet());

        return processors;

    }

}
