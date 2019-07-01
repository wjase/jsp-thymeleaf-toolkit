/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.util;

import java.util.Map;
import org.apache.commons.lang3.text.StrLookup;

/**
 * Adds some spice to the plain ${var} syntax which looks like this:
 *
 * ${var|defaultValue!postProcessFunction}
 *
 * eg
 *
 * ${myVal|Not There!ucFirst}
 *
 * Now it has a default value if the var is not present. It also has a
 * post-process function which can do things like case changes. Any function
 * registered with StringTransformers can be applied.
 *
 * @see StringTransformers
 */
class ValueResolverWithDefaultAndFilter extends StrLookup<String>
{

    private final Map<String, Object> values;

    public ValueResolverWithDefaultAndFilter(Map<String, Object> values)
    {
        this.values = values;
    }

    @Override
    public String lookup(String key)
    {
        return ValueDefaultAndFilter.parse(key).getFromMap(values);
    }

}
