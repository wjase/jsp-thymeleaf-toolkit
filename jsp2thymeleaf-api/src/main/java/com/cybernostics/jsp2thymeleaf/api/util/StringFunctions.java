/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author jason
 */
public class StringFunctions {

    public static String ucFirst(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static String stripAll(String s) {

        return stripEL(trimQuotes(s));
    }

    public static String stripEL(String s) {
        if (!s.startsWith("${")) {
            return s;
        }
        if (s.equals("${}")) {
            return "";
        }
        return s.substring(2, s.length() - 1);
    }

    public static String addCommaPrefix(String item) {
        if (item.length() == 0) {
            return item;
        }
        return String.format(",%s", item);
    }

    public static String humanReadable(String input) {
        return Arrays.asList(trimQuotes(input).replaceAll("(^\\$\\{)|(\\}$)", "")
                .split("\\."))
                .stream()
                .map(StringFunctions::ucFirst)
                .collect(Collectors.joining());
    }

    public static String trimQuotes(String text) {
        if ((text.startsWith("\"") && text.endsWith("\"")) || (text.startsWith("\'") && text.endsWith("\'"))) {
            return text.substring(1, text.length() - 1);
        }
        return text;
    }

    public static String singleQuoted(String text) {
        return String.format("'%s'", text.replaceAll("'", "\\'"));
    }
}
