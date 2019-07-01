/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.util;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jason
 */
public class ExpressionStringTemplateTest
{

    public ExpressionStringTemplateTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of generate method, of class SimpleStringTemplateProcessor.
     */
    @Test
    public void testGenerate_String_Map()
    {
        String inputFormat = "%{key2!stripEL}%{key1}%{key1!ucFirst}";
        Map<String, Object> values = new HashMap<>();
        values.put("key1", "value1");
        values.put("key2", "${value2}");

        String expResult = "value2value1Value1";
        String result = SimpleStringTemplateProcessor.generate(inputFormat, values);
        assertThat(result, is(expResult));
    }

    @Test
    public void testGenerateEmbeddedMap()
    {

        String inputFormat = "%{aMap!kvMap}";
        Map<String, String> embeddedMap = new HashMap<>();
        embeddedMap.put("item1", "\"value1\"");
        embeddedMap.put("item2", "${value2}");
        Map<String, Object> values = new HashMap<>();
        values.put("aMap", embeddedMap);

        String expResult = "item1=\"value1\",item2=${value2}";
        String result = SimpleStringTemplateProcessor.generate(inputFormat, values);
        assertThat(result, is(expResult));
    }

}
