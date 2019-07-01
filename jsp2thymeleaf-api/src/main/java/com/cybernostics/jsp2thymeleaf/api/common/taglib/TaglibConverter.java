/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.common.taglib;

import com.cybernostics.jsp2thymeleaf.api.elements.TagConverterSource;
import com.cybernostics.jsp2thymeleaf.api.expressions.function.FunctionConverterSource;

/**
 *
 * @author jason
 */
public interface TaglibConverter
{

    String forUri();

    TagConverterSource getTagConverter();

    FunctionConverterSource getFunctionConverter();

}
