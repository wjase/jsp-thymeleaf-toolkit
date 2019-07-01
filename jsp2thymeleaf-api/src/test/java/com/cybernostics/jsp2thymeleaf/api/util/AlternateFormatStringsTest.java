/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 *
 * @author jason
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class AlternateFormatStringsTest
{

    String[] formats =
    {
        "This one needs %{key1}%{key4!addCommaPrefix}%{key2}_<_some text %{key5}_>_",
        "This other one needs %{key1}%{key2!addCommaPrefix}%{key3}"
    };

    /**
     * Test of chooseFormat method, of class AlternateFormatStrings.
     */
    @Test
    public void shouldSelectAppropriateFormatForAvailableValues()
    {

        Map<String, Object> valMap = new HashMap<>();
        valMap.put("key1", "val1");
        valMap.put("key2", "val2");
        valMap.put("key3", "val3");
        Function<Map<String, Object>, String> result = AlternateFormatStrings.chooseFormat(formats);
        assertThat(result.apply(valMap), is("This other one needs val1,val2val3"));
        valMap.put("key4", "val4");
        assertThat(result.apply(valMap), is("This one needs val1,val4val2"));
        valMap.put("key5", "val5");
        assertThat(result.apply(valMap), is("This one needs val1,val4val2some text val5"));
    }

}
