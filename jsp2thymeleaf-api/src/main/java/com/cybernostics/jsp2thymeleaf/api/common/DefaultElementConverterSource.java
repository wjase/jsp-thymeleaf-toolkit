/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.common;

import com.cybernostics.jsp.parser.JSPParser;
import com.cybernostics.jsp2thymeleaf.api.elements.CopyElementConverter;
import com.cybernostics.jsp2thymeleaf.api.elements.TagConverter;
import com.cybernostics.jsp2thymeleaf.api.elements.TagConverterSource;
import com.cybernostics.jsp2thymeleaf.api.util.PrefixedName;
import java.util.Optional;
import com.cybernostics.jsp2thymeleaf.api.elements.JSPElementNodeConverter;

/**
 *
 * @author jason
 */
public class DefaultElementConverterSource extends TagConverterSource
{

    private static TagConverter[] tags = new TagConverter[0];
    CopyElementConverter converter = new CopyElementConverter();

    public DefaultElementConverterSource()
    {
        super("", tags);
    }

    @Override
    public Optional<JSPElementNodeConverter> converterFor(JSPParser.JspElementContext jspTree)
    {
        return Optional.of(converter);
    }

    @Override
    public Optional<JSPElementNodeConverter> converterFor(PrefixedName domTag)
    {
        return Optional.of(converter);
    }

}
