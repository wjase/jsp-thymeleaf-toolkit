package com.cybernostics.jsp2thymeleaf.sampleapp;

import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
public class SampleJspApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SampleJspApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleJspApplication.class, args);
    }

    @Bean
    @Qualifier("addResourceHandlersFunction")
    public Consumer<ResourceHandlerRegistry> resourceHandlersFunction() {
        System.out.println("registered jsp bean");
        return (r) -> {
            r.addResourceHandler("/jsp*/**").addResourceLocations("classpath:/");
                    
        };
    }
    


}
