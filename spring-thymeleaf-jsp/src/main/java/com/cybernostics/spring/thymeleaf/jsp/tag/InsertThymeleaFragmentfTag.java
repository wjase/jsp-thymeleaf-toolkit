/*
 * Copyright (c) 2016 Cybernostics Pty Ltd.
 * All rights reserved.
 */
package com.cybernostics.spring.thymeleaf.jsp.tag;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring4.SpringTemplateEngine;

/**
 * InsertThymeleaFragmentfTag - renders ThymeLeaf fragments into a JSP page
 * <p>
 * Allows you to migrate your header, then your footer then the page without
 * having to do all 3 at once.
 */
public class InsertThymeleaFragmentfTag extends SimpleTagSupport
{

    /**
     * The name of the ThymeLeaf template to render. The location is controlled
     * by the standard Thymeleaf prefix and suffix to build a complete URL.
     * <p>
     * eg - with the following properties in application.properties:
     */
    private String name;

    /**
     * An optional css selector to render only a fragment of the specified
     * ThymeLeaf template
     */
    private String fragment;

    @Override
    public void doTag() throws JspException, IOException
    {
        JspWriter out = getJspContext().getOut();
        PageContext page = (PageContext) getJspContext();

        SpringTemplateEngine springTemplateEngine = WebApplicationContextUtils.getWebApplicationContext(((PageContext) getJspContext()).getServletContext()).getBean(SpringTemplateEngine.class);

        HttpServletRequest request = (HttpServletRequest) page.getRequest();
        WebContext webContext = new WebContext(request, (HttpServletResponse) page.getResponse(), request.getServletContext());

        if (!StringUtils.isBlank(fragment))
        {
            out.println(springTemplateEngine.process(fragment, webContext));
        }

    }

    public void setLocation(String name)
    {
        this.name = name;
    }

    public void setSelector(String fragment)
    {
        this.fragment = fragment;
    }
}
