/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.util;

import static com.cybernostics.jsp2thymeleaf.api.util.KeyValueMapFormatter.expandMap;
import java.util.Map;
import org.apache.commons.lang3.text.StrLookup;
import org.apache.commons.lang3.text.StrSubstitutor;

/**
 *
 * @author jason
 */
public class SimpleStringTemplateProcessor
{

    static
    {
        StringTransformers.add("kvMap", o -> expandMap(o));

    }

    private final String format;

    public SimpleStringTemplateProcessor(String format)
    {
        this.format = format;
    }

    public String generate(Map<String, Object> values)
    {
        return getSubstitutor(values).replace(format);
    }

    public static StrSubstitutor getSubstitutor(Map<String, Object> values)
    {
        return getSubstitutor(new ValueResolverWithDefaultAndFilter(values));
    }

    public static StrSubstitutor getSubstitutor(StrLookup<String> variableResolver)
    {
        StrSubstitutor strSubstitutor = new StrSubstitutor(variableResolver);
        strSubstitutor.setEscapeChar('%');
        strSubstitutor.setVariablePrefix("%{");
        strSubstitutor.setEnableSubstitutionInVariables(false);
        return strSubstitutor;
    }

    public static String generate(String inputFormat, Map<String, Object> values)
    {
        return getSubstitutor(values).replace(inputFormat);
    }

}
