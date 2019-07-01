/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author jason
 */
public class SetUtils
{
    public static <T> Set<T> setOf(T...tList){
        return Arrays.asList(tList).stream().collect(Collectors.toSet());
    } 
}
