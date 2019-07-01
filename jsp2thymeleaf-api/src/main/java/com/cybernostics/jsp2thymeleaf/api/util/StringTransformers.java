/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

/**
 *
 * @author jason
 */
public class StringTransformers
{

    private static Map<Object, Function<Object, String>> functionMap = new TreeMap<>();

    private static Function<Object, String> identity = item -> item.toString();

    static
    {
        add(StringFunctions.class);
    }

    private static Object[] args(Object... args)
    {
        return args;
    }

    private static Function<Object, String> supplierFor(Method method)
    {
        return (item) ->
        {
            try
            {
                return (String) method.invoke(null, args(item));
            } catch (Exception ex)
            {
                throw new RuntimeException(ex);
            }

        };
    }

    private StringTransformers()
    {
    }

    public static void add(Class<?> utilityClass)
    {
        Arrays.stream(utilityClass
                .getMethods())
                .filter(method -> method.getReturnType().equals(String.class)
                && method.getParameterCount() == 1
                && method.getParameterTypes()[0].equals(String.class)
                )
                .forEach(method -> add(method.getName(), supplierFor(method)));
    }

    public static void add(String name, Function<Object, String> fn)
    {
        functionMap.put(name, fn);
    }

    public static Function<Object, String> get(String name)
    {
        if (!functionMap.containsKey(name))
        {
            throw new IllegalArgumentException("Unknown string transformer:" + name);
        }
        return functionMap.getOrDefault(name, identity);
    }
}
