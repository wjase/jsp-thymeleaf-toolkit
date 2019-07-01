/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.util;

/**
 *
 * @author jason
 */
public class PrefixedName
{

    public static PrefixedName prefixedNameFor(String fullName)
    {
        return new PrefixedName(fullName);
    }

    public static PrefixedName prefixedNameFor(String prefix, String name)
    {
        return new PrefixedName(prefix, name);
    }

    private String prefix;

    private String name;

    private PrefixedName(String wholeTag)
    {
        final String[] parts = wholeTag.split(":");
        if (parts.length > 1)
        {
            this.prefix = parts[0];
            this.name = parts[1];
        } else
        {
            this.prefix = "";
            this.name = parts[0];
        }
    }

    private PrefixedName(String prefix, String tagname)
    {
        this.prefix = prefix;
        this.name = tagname;

    }

    public String getPrefix()
    {
        return prefix;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return prefix + ":" + name;
    }

}
