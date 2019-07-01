/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.spring.thymeleaf.jsp.config;

import com.cybernostics.spring.thymeleaf.jsp.compatability.SpringStandardDialectWithJSPBehaviours;
import static java.util.Arrays.stream;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 *
 * @author jason
 */
@Configuration
@AutoConfigureBefore(ThymeleafAutoConfiguration.class)
public class SpringTemplateEngineAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(IDialect.class)
    SpringStandardDialect defaultDialect() {
        final SpringStandardDialectWithJSPBehaviours springStandardDialectWithJSPBehaviours = new SpringStandardDialectWithJSPBehaviours();
        //springStandardDialectWithJSPBehaviours.setExpressionParser(expressionParser);
        return springStandardDialectWithJSPBehaviours;
    }

    @Bean
    @ConditionalOnMissingBean(SpringTemplateEngine.class)
    SpringTemplateEngine springTemplateEngine(ITemplateResolver templateResolver, IDialect[] dialects) {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();

        stream(dialects)
                .peek(d-> System.out.println(d.getName()))
//                .filter(dialect-> !dialect.getClass().getName().equals("SpringStandardDialect"))
                .forEach(dialect -> {
                    System.out.println(dialect.getName());
                    springTemplateEngine.setDialect(dialect);
                        });
       springTemplateEngine.addTemplateResolver(templateResolver);
        return springTemplateEngine;
    }
}
