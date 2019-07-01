/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.elements;

import com.cybernostics.jsp.parser.JSPParser;
import com.cybernostics.jsp2thymeleaf.api.util.PrefixedName;
import static com.cybernostics.jsp2thymeleaf.api.util.PrefixedName.prefixedNameFor;
import org.jdom2.Attribute;

/**
 *
 * @author jason
 */
public class AttributeValueElementConverter
{

    Attribute transform(JSPParser.HtmlAttributeContext attribute)
    {
        final JSPParser.JspElementContext jspElement = attribute.jspElement();
        if (jspElement != null)
        {
            throw new UnsupportedOperationException("TODO");
        }
        final JSPParser.ScriptletContext scriptlet = attribute.scriptlet();
        if (scriptlet != null)
        {
            throw new UnsupportedOperationException("TODO");
        }
        final JSPParser.HtmlAttributeNameContext name = attribute.name;
        final JSPParser.HtmlAttributeValueContext value = attribute.value;
        String nameStr = name.getText();
        PrefixedName prefixedName = prefixedNameFor(nameStr);
//        if (prefixedName.getPrefix().equals("c"))
//        {
//            if (prefixedName.getName().equals("url"))
//            {
//                Optional<JSPAttributeNode> value = getAttribute(treeValue, "value");
//
//                return new Attribute(attName, "@{" + value.get().getValue() + "}");
//            }
//        }
        return new Attribute(nameStr, "Unknown tag:" + ((value == null) ? "null" : value.toStringTree()));

    }

}
