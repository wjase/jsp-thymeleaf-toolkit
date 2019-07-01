/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.util;

import java.util.Map;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author jason
 */
public class KeyValueMapFormatter
{

    public static String expandMap(Object o)
    {
        return ((Map<String, String>) o)
                .entrySet()
                .stream()
                .sorted((a1, a2) -> a1.getKey().compareTo(a2.getKey()))
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(joining(","));
    }
}
