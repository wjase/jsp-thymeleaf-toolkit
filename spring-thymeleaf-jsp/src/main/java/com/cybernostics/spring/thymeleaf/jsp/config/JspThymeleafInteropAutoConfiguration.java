/*
 * Copyright (c) 2016 Cybernostics Pty Ltd.
 * All rights reserved.
 */
package com.cybernostics.spring.thymeleaf.jsp.config;

import com.cybernostics.spring.thymeleaf.jsp.compatability.PageMap;
import com.cybernostics.spring.thymeleaf.jsp.compatability.SpringStandardDialectWithJSPBehaviours;
import com.cybernostics.spring.thymeleaf.jsp.view.DefaultTemplateExistenceChecker;
import com.cybernostics.spring.thymeleaf.jsp.view.TemplateExistenceChecker;
import com.cybernostics.spring.thymeleaf.jsp.view.ThymeleafJSPViewResolver;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
@AutoConfigureAfter(value
        = {
            ThymeleafAutoConfiguration.class,
            WebMvcAutoConfiguration.class
        })
@EnableConfigurationProperties(ThymeleafProperties.class)
@Import(value={ComposableFunctionWebMvcConfigurerAdapter.class})
public class JspThymeleafInteropAutoConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    private Logger LOG = Logger.getLogger(JspThymeleafInteropAutoConfiguration.class.getName());

    @PostConstruct
    public void reorderThymeleaf() {
        ThymeleafViewResolver bean = applicationContext.getBean(ThymeleafViewResolver.class);
        LOG.fine("Reducing priority for ThymeleafViewResolver to allow the ThymeleafJSPViewResolver to prevail.");
        bean.setOrder(Ordered.LOWEST_PRECEDENCE);
    }

    @Bean
    @ConditionalOnMissingBean(TemplateExistenceChecker.class)
    public TemplateExistenceChecker templateExistanceChecker(ApplicationContext applicationContext, ThymeleafProperties thymeleafProperties) {
        return new DefaultTemplateExistenceChecker(thymeleafProperties, applicationContext);
    }

    @Bean
    @ConditionalOnMissingBean(ThymeleafJSPViewResolver.class)
    public ViewResolver thymeleafJSPViewResolver(InternalResourceViewResolver internalResourceViewResolver, ThymeleafViewResolver thymeleafViewResolver, TemplateExistenceChecker existenceChecker) {
        final ThymeleafJSPViewResolver thymeleafJSPViewResolver = new ThymeleafJSPViewResolver(internalResourceViewResolver, thymeleafViewResolver, existenceChecker);
        return thymeleafJSPViewResolver;
    }
    
    @ControllerAdvice
    public static class PageScopeController {

        @ModelAttribute(name = "pageScope")
        PageMap pageScope() {
            return new PageMap();
        }
    }
    
    @Bean
    public PageMap applicationScope() {
        return new PageMap();
    }

}
