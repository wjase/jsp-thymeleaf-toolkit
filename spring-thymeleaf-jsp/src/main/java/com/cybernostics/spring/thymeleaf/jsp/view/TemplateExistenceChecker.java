/*
 * Copyright (c) 2016 Cybernostics Pty Ltd.
 * All rights reserved.
 */
package com.cybernostics.spring.thymeleaf.jsp.view;

/**
 * The TemplateExistenceChecker interface is used to determine whether or not a
 * Thymeleaf template exists. The current ThymeleafViewResolver does not honour
 * the ViewResolver's contract to return null if it can't find a template file
 * because of things and reasons. So. The HybridViewResolver checks an instance
 * of this class to see if it should bother to ask Thymeleaf about it at all.
 *
 * @see DefaultTemplateExistenceChecker for the default implementation which uses the
 * prefix and suffix properties
 */
public interface TemplateExistenceChecker
{
    boolean templateExists(String withName);
}
