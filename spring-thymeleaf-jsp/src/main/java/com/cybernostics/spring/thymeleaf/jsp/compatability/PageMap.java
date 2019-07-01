/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.spring.thymeleaf.jsp.compatability;

import java.util.TreeMap;

/**
 *
 * @author jason
 */
public class PageMap extends TreeMap<String, Object>
{

    public Boolean quietPut(String key, Object value)
    {
        super.put(key, value);
        return false;
    }

    public Boolean quietPutIfAbsent(String key, Object value)
    {
        putIfAbsent(key, value);
        return false;
    }
}
