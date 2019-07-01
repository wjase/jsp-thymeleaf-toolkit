/*
 * Copyright (c) 2016 Cybernostics Pty Ltd.
 * All rights reserved.
 */
package com.cybernostics.spring.thymeleaf.jsp.view;

import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.ApplicationContext;

/**
 * Default implementation to find Thymeleaf templates based on prefix and suffix
 * properties
 */
public class DefaultTemplateExistenceChecker implements TemplateExistenceChecker
{

    private ThymeleafProperties thymeleafProperties;
    private ApplicationContext applicationContext;

    public DefaultTemplateExistenceChecker(ThymeleafProperties thymeleafProperties, ApplicationContext applicationContext)
    {
        this.thymeleafProperties = thymeleafProperties;
        this.applicationContext = applicationContext;
    }

    public String getThymeleafResourceUrlForName(String name)
    {
        return thymeleafProperties.getPrefix() + name + thymeleafProperties.getSuffix();
    }

    public boolean templateExists(String name)
    {
        return applicationContext.getResource(getThymeleafResourceUrlForName(name)).exists();
    }
}
