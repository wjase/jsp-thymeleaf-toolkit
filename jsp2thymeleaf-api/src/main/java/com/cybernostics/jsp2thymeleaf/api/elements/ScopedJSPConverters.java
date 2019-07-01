/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.elements;

import com.cybernostics.jsp2thymeleaf.api.common.DefaultElementConverterSource;
import com.cybernostics.jsp2thymeleaf.api.expressions.function.FunctionConverterSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ScopedJSPConverters keeps track of taglib converters which have been declared
 * with jsp taglib directives, and the prefixes with which they are associated.
 *
 * As the taglib is encountered, this is used to register the service.
 *
 * The forUri method is then used when tags with a given (or no) prefix are
 * encountered.
 *
 * @author wjase
 */
public class ScopedJSPConverters
{

    private Optional<ScopedJSPConverters> parentScope = Optional.empty();
    private static JSPNodeConverterSource DEFAULT_CONVERTER_SOURCE = new DefaultElementConverterSource();

    public ScopedJSPConverters()
    {
        addTaglibConverter("", new DefaultElementConverterSource());
    }

    public ScopedJSPConverters(ScopedJSPConverters parentScope)
    {
        this();
        this.parentScope = Optional.ofNullable(parentScope);
    }

    private Map<String, JSPNodeConverterSource> activeTagConverters = new HashMap<>();

    /**
     * Registers a given converter for handling tags with the prefix specified
     *
     * @param prefix
     * @param converterSource
     */
    public void addTaglibConverter(String prefix, JSPNodeConverterSource converterSource)
    {
        activeTagConverters.putIfAbsent(prefix, converterSource);
    }

    /**
     * Return the converter for a given prefix.
     *
     * @param prefix
     * @return
     */
    public Optional<JSPNodeConverterSource> forPrefix(String prefix)
    {
        JSPNodeConverterSource source = activeTagConverters.getOrDefault(prefix, null);
        if (source == null && parentScope.isPresent())
        {
            return parentScope.get().forPrefix(prefix);
        }
        return Optional.ofNullable(source);
    }

    private Map<String, FunctionConverterSource> activeExpressionConverters = new HashMap<>();

    /**
     * Registers a given converter for handling tags with the prefix specified
     *
     * @param prefix
     * @param converterSource
     */
    public void addTaglibFunctionConverter(String prefix, FunctionConverterSource converterSource)
    {
        activeExpressionConverters.putIfAbsent(prefix, converterSource);
    }

    /**
     * Return the converter for a given prefix.
     *
     * @param prefix
     * @return
     */
    public Optional<FunctionConverterSource> functionConverterForPrefix(String prefix)
    {
        FunctionConverterSource source = activeExpressionConverters.getOrDefault(prefix, null);
        if (source == null && parentScope.isPresent())
        {
            return parentScope.get().functionConverterForPrefix(prefix);
        }
        return Optional.ofNullable(source);
    }

    public static JSPNodeConverterSource defaultSource()
    {
        return DEFAULT_CONVERTER_SOURCE;
    }

}
