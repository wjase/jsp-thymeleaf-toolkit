/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author jason
 */
class ValueDefaultAndFilter
{

    private String name;
    private Optional<String> defaultValue;
    private List<String> filterNames;
    private List<Function<Object, String>> filters;

    public String getName()
    {
        return name;
    }

    public Optional<String> getDefaultValue()
    {
        return defaultValue;
    }

    public List<String> getFilterNames()
    {
        return filterNames;
    }

    public String getFromMap(Map<String, Object> source)
    {
        Object currentValue = source.getOrDefault(name, defaultValue.orElseGet(() -> ""));
        for (Function<Object, String> filter : filters)
        {
            currentValue = filter.apply(currentValue);
        }
        return currentValue.toString();
    }

    public static ValueDefaultAndFilter parse(String valueName)
    {
        ValueDefaultAndFilter value = new ValueDefaultAndFilter();
        value.name = valueName;
        value.defaultValue = Optional.empty();
        value.filterNames = new ArrayList();
        if (hasFilter(valueName))
        {
            String[] bits = valueName.split("!");
            value.name = bits[0];
            value.filterNames = Arrays.asList((bits.length > 1 ? bits[1] : "").split(","));
            if (value.filterNames.isEmpty())
            {
                throw new IllegalArgumentException("missing filter name for variable " + valueName);
            }
        }
        if (hasDefault(value.name))
        {
            String[] bits = value.name.split("\\|");
            value.name = bits[0];
            value.defaultValue = Optional.of(bits.length > 1 ? bits[1] : "");
        }
        value.filters = value.filterNames
                .stream()
                .map(filtName -> StringTransformers.get(filtName))
                .collect(toList());
        return value;
    }

    public static boolean hasDefault(String valueName)
    {
        return valueName.contains("|");
    }

    public static boolean hasFilter(String valueName)
    {
        return valueName.contains("!");
    }

    public boolean isRequired()
    {
        return !defaultValue.isPresent();
    }

}
