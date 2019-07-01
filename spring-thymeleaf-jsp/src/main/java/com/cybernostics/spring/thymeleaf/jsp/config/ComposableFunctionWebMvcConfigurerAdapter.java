package com.cybernostics.spring.thymeleaf.jsp.config;

/*-
 * #%L
 * spring-thymeleaf-jsp
 * %%
 * Copyright (C) 1992 - 2017 Cybernostics Pty Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * An implementation of {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurer} with functional consumers and
 * suppliers meaning that users don't need to subclass , but instead simply
 * implement the required bean(s) to configure what they want to configure.
 * 
 * This allows multiple libraries to selectively provide configurers without colliding.
 *
 * @author Jason Wraxall
 * @since 4.7
 */
@Configuration
@ConditionalOnProperty(name = "com.cybernostics.spring.thymeleaf.jsp.skipmvcconfig", havingValue = "false", matchIfMissing = true)
public class ComposableFunctionWebMvcConfigurerAdapter implements WebMvcConfigurer
{

    @Autowired(required = false)
    @Qualifier("configurePathMatchFunction")
    private Consumer<PathMatchConfigurer> configurePathMatchFunction = (f) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("configureContentNegotiationFunction")
    private Consumer<ContentNegotiationConfigurer> configureContentNegotiationFunction = (f) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("configureAsyncSupportFunction")
    private Consumer<AsyncSupportConfigurer> configureAsyncSupportFunction = (f) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("configureAsyncSupportFunction")
    private Consumer<DefaultServletHandlerConfigurer> configureDefaultServletHandlingFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("addFormattersFunction")
    private Consumer<FormatterRegistry> addFormattersFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("addInterceptorsFunction")
    private Consumer<InterceptorRegistry> addInterceptorsFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("addResourceHandlersFunction")
    private Consumer<ResourceHandlerRegistry> addResourceHandlersFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("addCorsMappingsFunction")
    private Consumer<CorsRegistry> addCorsMappingsFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("addViewControllersFunction")
    private Consumer<ViewControllerRegistry> addViewControllersFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("configureViewResolversFunction")
    private Consumer<ViewResolverRegistry> configureViewResolversFunction = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("formatterRegistryConfigurer")
    private Consumer<FormatterRegistry> formatterRegistryConfigurer = (f) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("addArgumentResolversFunction")
    private Consumer<List<HandlerMethodArgumentResolver>> addArgumentResolversFunction = (hm) ->
    {
    };
    @Autowired(required = false)
    private Consumer<List<HandlerMethodReturnValueHandler>> addReturnValueHandlersFunction = (hm) ->
    {
    };
    @Autowired(required = false)
    @Qualifier("configureMessageConvertersFunction")
    private Consumer<List<HttpMessageConverter<?>>> configureMessageConvertersFunction = (mc) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("extendMessageConvertersFunction")
    private Consumer<List<HttpMessageConverter<?>>> extendMessageConvertersFunction = (mc) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("configureHandlerExceptionResolversFunction")
    private Consumer<List<HandlerExceptionResolver>> configureHandlerExceptionResolversFunction = (l) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("extendHandlerExceptionResolversFunction")
    private Consumer<List<HandlerExceptionResolver>> extendHandlerExceptionResolversFunction = (f) ->
    {
    };

    @Autowired(required = false)
    @Qualifier("getValidatorFunction")
    private Supplier<Validator> getValidatorFunction = () -> null;

    @Autowired(required = false)
    @Qualifier("getMessageCodesResolverFunction")
    private Supplier<MessageCodesResolver> getMessageCodesResolverFunction = () -> null;

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt;PathMatchConfigurer&gt; named
     * configurePathMatchFunction to override this.
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer)
    {
        configurePathMatchFunction.accept(configurer);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt;ContentNegotiationConfigurer&gt; named
     * configureContentNegotiationFunction to override this.
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
    {
        configureContentNegotiationFunction.accept(configurer);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt;AsyncSupportConfigurer&gt; named
     * configureAsyncSupportFunction to override this.
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer)
    {
        configureAsyncSupportFunction.accept(configurer);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt;DefaultServletHandlerConfigurer&gt; named
     * configureDefaultServletHandlingFunction to override this.
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configureDefaultServletHandlingFunction.accept(configurer);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt;FormatterRegistry&gt; named
     * addFormattersFunctionFunction to override this.
     */
    @Override
    public void addFormatters(FormatterRegistry registry)
    {
        addFormattersFunction.accept(registry);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt;InterceptorRegistry&gt; named
     * addInterceptorsFunction to override this.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        addInterceptorsFunction.accept(registry);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt;ResourceHandlerRegistry&gt; named
     * addResourceHandlersFunction to override this.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        System.out.println("boo2");
        addResourceHandlersFunction.accept(registry);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt;CorsRegistry&gt; named
     * addCorsMappingsFunction to override this.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        addCorsMappingsFunction.accept(registry);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt;ViewControllerRegistry&gt; named
     * addViewControllersFunction to override this.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        addViewControllersFunction.accept(registry);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt;ViewResolverRegistry&gt; named
     * configureViewResolversFunction to override this.
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry)
    {
        configureViewResolversFunction.accept(registry);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt;List&lt;HandlerMethodArgumentResolver&gt;&gt;
     * named addArgumentResolversFunction to override this.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
    {
        addArgumentResolversFunction.accept(argumentResolvers);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt; List&lt;HandlerMethodReturnValueHandler&gt;&gt;
     * named addReturnValueHandlersFunction to override this.
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers)
    {
        addReturnValueHandlersFunction.accept(returnValueHandlers);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt;List&lt;HttpMessageConverter&lt;?&gt;&gt;&gt; named
     * configureMessageConvertersFunction to override this.
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        configureMessageConvertersFunction.accept(converters);
    }

    /**
     * {@inheritDoc}
     *
     * Declare a Bean of type Consumer &lt;List&lt;HttpMessageConverter&lt;?&gt;&gt; named
     * extendMessageConvertersFunction to override this.
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        extendMessageConvertersFunction.accept(converters);
    }

    /**
     * {@inheritDoc}
     * 
     * Declare a Bean of type Consumer&lt;List &lt; HandlerExceptionResolver&gt;&gt; named
     * configureHandlerExceptionResolversFunction to override this.
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers)
    {
        configureHandlerExceptionResolversFunction.accept(exceptionResolvers);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Consumer&lt;List&lt;HandlerExceptionResolver&gt;&gt; named
     * extendHandlerExceptionResolversFunction to override this.
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers)
    {
        extendHandlerExceptionResolversFunction.accept(exceptionResolvers);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Supplier&lt;Validator&gt; named getValidatorFunction to
     * override this.
     */
    @Override
    public Validator getValidator()
    {
        return getValidatorFunction.get();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Declare a Bean of type Supplier&lt;MessageCodesResolver&gt; named
     * getMessageCodesResolverFunction to override this.
     */
    @Override
    public MessageCodesResolver getMessageCodesResolver()
    {
        return getMessageCodesResolverFunction.get();
    }

}
