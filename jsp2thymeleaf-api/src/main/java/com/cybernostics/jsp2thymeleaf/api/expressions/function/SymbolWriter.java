/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.expressions.function;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.el.BinaryOperator;
import org.apache.commons.el.UnaryOperator;

/**
 * This class translates the long form symbols into their more common
 * equivalents TODO: Figure out which was used in the first place
 *
 * @author jason
 */
public class SymbolWriter
{

    private static Map<String, String> symbolMap = new HashMap<>();

    private static void add(String shortSymbol, String textSymbol)
    {
        symbolMap.put(textSymbol, shortSymbol);
    }

    static
    {
        add("/", "div");
        add("%", "mod");
        add("==", "eq");
        add("<", "lt");
        add(">", "gt");
        add("<=", "le");
        add(">=", "ge");
        add("&&", "and");
        add("||", "or");
        add("!", "not");

    }

    public static void write(Writer w, String s)
    {
        try
        {
            w.write(symbolMap.getOrDefault(s, s));
        } catch (IOException ex)
        {
            Logger.getLogger(SymbolWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void write(Writer w, BinaryOperator operator)
    {
        write(w, operator.getOperatorSymbol());
    }

    public static void write(Writer w, UnaryOperator operator)
    {
        write(w, operator.getOperatorSymbol());
    }

}
