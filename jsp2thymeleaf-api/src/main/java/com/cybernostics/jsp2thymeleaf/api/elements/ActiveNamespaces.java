/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.elements;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import org.jdom2.Namespace;

/**
 * Keeps a list of namespaces for dom elements which actually occur in the
 * transformed document.
 *
 * @see AvailableNamespaces
 * @author jason
 */
public class ActiveNamespaces
{

    private static final Map<String, Namespace> namespaces = new TreeMap<>();

    private ActiveNamespaces()
    {

    }

    public static void add(Namespace ns)
    {
        namespaces.putIfAbsent(ns.getURI(), ns);
    }

    public static Collection<Namespace> get()
    {
        return namespaces.values();
    }

    public static void reset()
    {
        namespaces.clear();
    }
}
