/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.converters.jstl.core;

import com.cybernostics.jsp2thymeleaf.api.common.AvailableConverters;
import static com.cybernostics.jsp2thymeleaf.api.common.Namespaces.TH;
import static com.cybernostics.jsp2thymeleaf.api.common.Namespaces.XMLNS;
import com.cybernostics.jsp2thymeleaf.api.common.taglib.ConverterRegistration;
import static com.cybernostics.jsp2thymeleaf.api.elements.JspTagElementConverter.converterFor;
import static com.cybernostics.jsp2thymeleaf.api.elements.JspTagElementConverter.ignore;
import com.cybernostics.jsp2thymeleaf.api.elements.TagConverterSource;
import static com.cybernostics.jsp2thymeleaf.api.util.AlternateFormatStrings.chooseFormat;
import static com.cybernostics.jsp2thymeleaf.api.util.AlternateFormatStrings.constant;
import static com.cybernostics.jsp2thymeleaf.api.elements.AttributeCreator.newAttribute;
import static com.cybernostics.jsp2thymeleaf.api.elements.AttributeCreator.newAttributeTH;

/**
 *
 * @author jason
 */
public class JstlCoreConverterRegistration implements ConverterRegistration
{

    @Override
    public void run()
    {
        final TagConverterSource jstlCoreTaglibConverterSource = new TagConverterSource()
                .withConverters(converterFor("if")
                                .withNewName("block", TH)
                                .renamesAttribute("test", "if", TH),
                        converterFor("choose")
                                .withNewName("block", TH)
                                .addsAttributes(newAttribute("choose", TH)
                                        .withValue(constant(""))),
                        converterFor("when")
                                .withNewName("block", TH)
                                .renamesAttribute("test", "when", TH),
                        converterFor("otherwise")
                                .withNewName("block", TH)
                                .addsAttributes(newAttributeTH("otherwise")
                                        .withValue(constant(""))),
                        converterFor("out")
                                .withNewName("span", XMLNS)
                                .removesAttributes("value", "var", "scope", "context")
                                .addsAttributes(newAttributeTH("text")
                                        .withValue(chooseFormat(
                                                "#setValue('%{var}','%{scope|page}',%{value})",
                                                "%{value}"))
                                )
                                .whenQuotedInAttributeReplaceWith("text")
                                .withNewTextContent("%{value!humanReadable}"),
                        converterFor("forTokens")
                                .withNewName("block", TH)
                                .removesAttributes("var", "varStatus", "items", "delims")
                                .addsAttributes(newAttributeTH("each")
                                        .withValue(chooseFormat(
                                                "%{var}%{varStatus|!addCommaPrefix} : ${#strings.split('%{items}'%{delims|!singleQuoted,addCommaPrefix})"))),
                        converterFor("forEach")
                                .withNewName("block", TH)
                                .removesAttributes("var", "begin", "end", "step", "varStatus", "items", "step")
                                .addsAttributes(newAttributeTH("each")
                                        .withValue(chooseFormat(
                                                "%{var}%{varStatus|!addCommaPrefix} : %{items}",
                                                "%{var}%{varStatus|!addCommaPrefix} : ${#numbers.sequence(%{begin},%{end}%{step|!addCommaPrefix})}"))),
                        converterFor("set")
                                .withNewName("span")
                                .removesAttributes("var", "scope", "value")
                                .addsAttributes(newAttribute("if", TH)
                                        .withValue(
                                                chooseFormat("${%{scope|page}Scope.put('%{var}',%{value!stripEL})}")
                                        )),
                        converterFor("url")
                                .withChildElementAtributes((nodeAndContext) ->
                                {
                                    return nodeAndContext.paramsBy("name", "value");
                                })
                                .withNewName("span", XMLNS)
                                .removesAttributes("value", "var", "scope", "context")
                                .addsAttributes(newAttributeTH("text")
                                        .withValue(chooseFormat(
                                                "${%{scope|page}Params.put('%{var}',@{_<_~%{context}_>_%{value}_<_(%{_childAtts!kvMap})_>_})",
                                                "@{_<_~%{context}_>_%{value}_<_(%{_childAtts!kvMap})_>_}"))
                                )
                                .whenQuotedInAttributeReplaceWith("text"),
                        ignore("param")
                );

        AvailableConverters.addConverter("http://java.sun.com/jstl/core", jstlCoreTaglibConverterSource);
        AvailableConverters.addConverter("http://java.sun.com/jsp/jstl/core", jstlCoreTaglibConverterSource);
    }

}
