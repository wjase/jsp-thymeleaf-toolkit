/*
 * Copyright (c) 2016 Cybernostics Pty Ltd.
 * All rights reserved.
 */
package com.cybernostics.spring.thymeleaf.jsp.view;

import java.util.Locale;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

/**
 * This class forces the JSP and Thymeleaf view resolvers to play nicely with
 * each other and not try to assume they are the only show in town.
 *
 * When resolving views, the assumption is that the Thymeleaf view will be used
 * if a template exists for the view name, and the JSP will be used if not.
 *
 * @author jason
 */
public class ThymeleafJSPViewResolver implements Ordered, ViewResolver
{

    private final InternalResourceViewResolver internalResourceViewResolver;
    private final ThymeleafViewResolver thymeleafViewResolver;
    private TemplateExistenceChecker templateExistenceChecker;

    public ThymeleafJSPViewResolver(InternalResourceViewResolver internalResourceViewResolver,
            ThymeleafViewResolver thymeleafViewResolver,
            TemplateExistenceChecker templateLocator)
    {

        this.internalResourceViewResolver = internalResourceViewResolver;
        this.thymeleafViewResolver = thymeleafViewResolver;
        this.templateExistenceChecker = templateLocator;
    }

    public int getOrder()
    {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    public View resolveViewName(String viewName, Locale locale) throws Exception
    {
        if (templateExistenceChecker.templateExists(viewName))
        {
            return thymeleafViewResolver.resolveViewName(viewName, locale);
        }
        return internalResourceViewResolver.resolveViewName(viewName, locale);
    }
}
