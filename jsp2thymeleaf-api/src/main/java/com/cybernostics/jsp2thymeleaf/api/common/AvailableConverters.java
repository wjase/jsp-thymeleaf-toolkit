/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.common;

import com.cybernostics.jsp2thymeleaf.api.elements.TagConverterSource;
import com.cybernostics.jsp2thymeleaf.api.expressions.function.FunctionConverterSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Maintains a pool of available converters for taglibs based on the URI. In
 * some cases different or custom URIs are used to
 *
 * @author jason
 */
public class AvailableConverters
{

    private static final Map<String, TagConverterSource> availableTagConverters = new HashMap<>();
    private static final Map<String, FunctionConverterSource> availableExpressionConverters = new HashMap<>();

    public static void addConverter(String forURI, TagConverterSource converterSource)
    {
        availableTagConverters.put(forURI, converterSource);
    }

    public static void addConverter(String forURI, FunctionConverterSource converterSource)
    {
        availableExpressionConverters.put(forURI, converterSource);
    }

    public static void addConverter(TagConverterSource converterSource)
    {
        availableTagConverters.put(converterSource.getTaglibURI(), converterSource);
    }

    public static void addConverter(FunctionConverterSource converterSource)
    {
        availableExpressionConverters.put(converterSource.getTaglibURI(), converterSource);
    }

    public static Optional<TagConverterSource> elementConverterforUri(String uri)
    {
        return Optional.ofNullable(availableTagConverters.getOrDefault(uri, null));
    }

    public static Optional<FunctionConverterSource> functionConverterforUri(String uri)
    {
        return Optional.ofNullable(availableExpressionConverters.getOrDefault(uri, null));
    }

    public static void addUriAlias(String existingUri, String aliasUri)
    {
        if (!availableTagConverters.containsKey(existingUri))
        {
            if (availableExpressionConverters.containsKey(existingUri))
            {
                throw new IllegalArgumentException("Unknown URI:" + existingUri);
            } else
            {
                availableExpressionConverters.put(aliasUri, availableExpressionConverters.get(existingUri));
            }
        } else
        {
            availableTagConverters.put(aliasUri, availableTagConverters.get(existingUri));
        }
    }

    private TagConverterSource defaultConverterSource = new DefaultElementConverterSource();
    private FunctionConverterSource defaultFunctionConverterSource = new DefaultFunctionConverterSource();

    public TagConverterSource getDetaultTagConverter()
    {
        return defaultConverterSource;
    }

    public FunctionConverterSource getDefaultFunctionConverterSource()
    {
        return defaultFunctionConverterSource;
    }

    public static void reset()
    {
        availableTagConverters.clear();
        availableExpressionConverters.clear();
    }

}
