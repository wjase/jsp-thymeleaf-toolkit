package com.cybernostics.jsp2thymeleaf.conveters.tld;

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
public class SpringTldConverterRegistration implements ConverterRegistration
{

    @Override
    public void run()
    {
        final TagConverterSource springTaglibConverterSource = new TagConverterSource()
                .withConverters(/* Sets default HTML escape value for the current page.
		Overrides a "defaultHtmlEscape" context-param in web.xml, if any.  */
                        converterFor("htmlEscape")
                                // attributes
                                // defaultHtmlEscape - Set the default value for HTML escaping, to be put into the current PageContext.
                                .withNewName("div", XMLNS) //.removesAtributes(#quotedList($tag.attributes))
                        //.addsAttributes(NewAttributeBuilder.newAttributeNamed("object", TH)
                        //      .withValue("chooseFormat("${%{commandName}}"))//,
                        //      .withNewTextContent("%{value!humanReadable}"),
                        ,
                        /* Escapes its enclosed body content, applying HTML escaping and/or JavaScript
		escaping. The HTML escaping flag participates in a page-wide or application-wide setting
		(i.e. by HtmlEscapeTag or a "defaultHtmlEscape" context-param in web.xml).  */
                         converterFor("escapeBody")
                                // attributes
                                // htmlEscape - Set HTML escaping for this tag, as boolean value. Overrides the default HTML escaping setting for the current page.
                                // javaScriptEscape - Set JavaScript escaping for this tag, as boolean value. Default is 'false'.
                                .withNewName("escapeBody", XMLNS) //.removesAtributes(#quotedList($tag.attributes))
                        //.addsAttributes(NewAttributeBuilder.newAttributeNamed("object", TH)
                        //      .withValue("chooseFormat("${%{commandName}}"))//,
                        //      .withNewTextContent("%{value!humanReadable}"),
                        ,
                        /* Retrieves the message with the given code, or text if code isn't resolvable.
		The HTML escaping flag participates in a page-wide or application-wide setting
		(i.e. by HtmlEscapeTag or a "defaultHtmlEscape" context-param in web.xml).  */
                         converterFor("message")
                                // attributes
                                // message - A MessageSourceResolvable argument (direct or through JSP EL). Fits nicely when used in conjunction with Spring's own validation error classes which all implement the MessageSourceResolvable interface. For example, this allows you to iterate over all of the errors in a form, passing each error (using a runtime expression) as the value of this 'message' attribute, thus effecting the easy display of such error messages.
                                // code - The code (key) to use when looking up the message. If code is not provided, the text attribute will be used.
                                // arguments - Set optional message arguments for this tag, as a (comma-) delimited String (each String argument can contain JSP EL), an Object array (used as argument array), or a single Object (used as single argument). You can additionally use nested spring:argument tags.
                                // argumentSeparator - The separator character to be used for splitting the arguments string value; defaults to a 'comma' (',').
                                // text - Default text to output when a message for the given code could not be found. If both text and code are not set, the tag will output null.
                                // var - The string to use when binding the result to the page, request, session or application scope. If not specified, the result gets outputted to the writer (i.e. typically directly to the JSP).
                                // scope - The scope to use when exporting the result to a variable. This attribute is only used when var is also set. Possible values are page, request, session and application.
                                // htmlEscape - Set HTML escaping for this tag, as boolean value. Overrides the default HTML escaping setting for the current page.
                                // javaScriptEscape - Set JavaScript escaping for this tag, as boolean value. Default is 'false'.
                                .withNewName("message", XMLNS) //.removesAtributes(#quotedList($tag.attributes))
                        //.addsAttributes(NewAttributeBuilder.newAttributeNamed("object", TH)
                        //      .withValue("chooseFormat("${%{commandName}}"))//,
                        //      .withNewTextContent("%{value!humanReadable}"),
                        ,
                        /* Retrieves the theme message with the given code, or text if code isn't
		resolvable. The HTML escaping flag participates in a page-wide or application-wide setting
		(i.e. by HtmlEscapeTag or a "defaultHtmlEscape" context-param in web.xml).  */
                         converterFor("theme")
                                // attributes
                                // message - A MessageSourceResolvable argument (direct or through JSP EL).
                                // code - The code (key) to use when looking up the message. If code is not provided, the text attribute will be used.
                                // arguments - Set optional message arguments for this tag, as a (comma-) delimited String (each String argument can contain JSP EL), an Object array (used as argument array), or a single Object (used as single argument). You can additionally use nested spring:argument tags.
                                // argumentSeparator - The separator character to be used for splitting the arguments string value; defaults to a 'comma' (',').
                                // text - Default text to output when a message for the given code could not be found. If both text and code are not set, the tag will output null.
                                // var - The string to use when binding the result to the page, request, session or application scope. If not specified, the result gets outputted to the writer (i.e. typically directly to the JSP).
                                // scope - The scope to use when exporting the result to a variable. This attribute is only used when var is also set. Possible values are page, request, session and application.
                                // htmlEscape - Set HTML escaping for this tag, as boolean value. Overrides the default HTML escaping setting for the current page.
                                // javaScriptEscape - Set JavaScript escaping for this tag, as boolean value. Default is 'false'.
                                .withNewName("theme", XMLNS) //.removesAtributes(#quotedList($tag.attributes))
                        //.addsAttributes(NewAttributeBuilder.newAttributeNamed("object", TH)
                        //      .withValue("chooseFormat("${%{commandName}}"))//,
                        //      .withNewTextContent("%{value!humanReadable}"),
                        ,
                        /* Argument tag based on the JSTL fmt:param tag. The purpose is to
		support arguments inside the spring:message and spring:theme tags.  */
                         converterFor("argument")
                                // attributes
                                // value - The value of the argument.
                                .withNewName("argument", XMLNS) //.removesAtributes(#quotedList($tag.attributes))
                        //.addsAttributes(NewAttributeBuilder.newAttributeNamed("object", TH)
                        //      .withValue("chooseFormat("${%{commandName}}"))//,
                        //      .withNewTextContent("%{value!humanReadable}"),
                        ,
                        /* Provides Errors instance in case of bind errors. The HTML escaping
		flag participates in a page-wide or application-wide setting (i.e. by HtmlEscapeTag
		or a "defaultHtmlEscape" context-param in web.xml).  */
                         converterFor("hasBindErrors")
                                // attributes
                                // name - The name of the bean in the request, that needs to be inspected for errors. If errors are available for this bean, they will be bound under the 'errors' key.
                                // htmlEscape - Set HTML escaping for this tag, as boolean value. Overrides the default HTML escaping setting for the current page.
                                .withNewName("hasBindErrors", XMLNS) //.removesAtributes(#quotedList($tag.attributes))
                        //.addsAttributes(NewAttributeBuilder.newAttributeNamed("object", TH)
                        //      .withValue("chooseFormat("${%{commandName}}"))//,
                        //      .withNewTextContent("%{value!humanReadable}"),
                        ,
                        /* Sets a nested path to be used by the bind tag's path.  */
                         converterFor("nestedPath")
                                // attributes
                                // path - Set the path that this tag should apply. E.g. 'customer' to allow bind paths like 'address.street' rather than 'customer.address.street'.
                                .withNewName("nestedPath", XMLNS) //.removesAtributes(#quotedList($tag.attributes))
                        //.addsAttributes(NewAttributeBuilder.newAttributeNamed("object", TH)
                        //      .withValue("chooseFormat("${%{commandName}}"))//,
                        //      .withNewTextContent("%{value!humanReadable}"),
                        ,
                        /* Provides BindStatus object for the given bind path. The HTML escaping
		flag participates in a page-wide or application-wide setting (i.e. by HtmlEscapeTag
		or a "defaultHtmlEscape" context-param in web.xml).  */
                         converterFor("bind")
                                // attributes
                                // path - The path to the bean or bean property to bind status information for. For instance account.name, company.address.zipCode or just employee. The status object will exported to the page scope, specifically for this bean or bean property.
                                // ignoreNestedPath - Set whether to ignore a nested path, if any. Default is to not ignore.
                                // htmlEscape - Set HTML escaping for this tag, as boolean value. Overrides the default HTML escaping setting for the current page.
                                .withNewName("bind", XMLNS) //.removesAtributes(#quotedList($tag.attributes))
                        //.addsAttributes(NewAttributeBuilder.newAttributeNamed("object", TH)
                        //      .withValue("chooseFormat("${%{commandName}}"))//,
                        //      .withNewTextContent("%{value!humanReadable}"),
                        ,
                        /* Provides transformation of variables to Strings, using an appropriate
		custom PropertyEditor from BindTag (can only be used inside BindTag). The HTML
		escaping flag participates in a page-wide or application-wide setting (i.e. by
		HtmlEscapeTag or a 'defaultHtmlEscape' context-param in web.xml).  */
                         converterFor("transform")
                                // attributes
                                // value - The value to transform. This is the actual object you want to have transformed (for instance a Date). Using the PropertyEditor that is currently in use by the 'spring:bind' tag.
                                // var - The string to use when binding the result to the page, request, session or application scope. If not specified, the result gets outputted to the writer (i.e. typically directly to the JSP).
                                // scope - The scope to use when exported the result to a variable. This attribute is only used when var is also set. Possible values are page, request, session and application.
                                // htmlEscape - Set HTML escaping for this tag, as boolean value. Overrides the default HTML escaping setting for the current page.
                                .withNewName("transform", XMLNS) //.removesAtributes(#quotedList($tag.attributes))
                        //.addsAttributes(NewAttributeBuilder.newAttributeNamed("object", TH)
                        //      .withValue("chooseFormat("${%{commandName}}"))//,
                        //      .withNewTextContent("%{value!humanReadable}"),
                        ,
                        /* URL tag based on the JSTL c:url tag. This variant is fully
		backwards compatible with the standard tag. Enhancements include support
		for URL template parameters.  */
                         converterFor("url")
                                // attributes
                                // value - The URL to build. This value can include template place holders that are replaced with the URL encoded value of the newAttributeNamed parameter. Parameters must be defined using the param tag inside the body of this tag.
                                // context - Specifies a remote application context path. The default is the current application context path.
                                // var - The name of the variable to export the URL value to.
                                // scope - The scope for the var. 'application', 'session', 'request' and 'page' scopes are supported. Defaults to page scope. This attribute has no effect unless the var attribute is also defined.
                                // htmlEscape - Set HTML escaping for this tag, as a boolean value. Overrides the default HTML escaping setting for the current page.
                                // javaScriptEscape - Set JavaScript escaping for this tag, as a boolean value. Default is 'false'.
                                .withNewName("url", XMLNS) //.removesAtributes(#quotedList($tag.attributes))
                        //.addsAttributes(NewAttributeBuilder.newAttributeNamed("object", TH)
                        //      .withValue("chooseFormat("${%{commandName}}"))//,
                        //      .withNewTextContent("%{value!humanReadable}"),
                        ,
                        /* Parameter tag based on the JSTL c:param tag. The sole purpose is to
		support params inside the spring:url tag.  */
                         ignore("param")
                                // attributes
                                // name - The name of the parameter.
                                // value - The value of the parameter.
                                .withNewName("param", XMLNS) //.removesAtributes(#quotedList($tag.attributes))
                        //.addsAttributes(NewAttributeBuilder.newAttributeNamed("object", TH)
                        //      .withValue("chooseFormat("${%{commandName}}"))//,
                        //      .withNewTextContent("%{value!humanReadable}"),
                        ,
                         converterFor("eval") //  eval
                                // attributes
                                // expression - The expression to evaluate.
                                // var - The name of the variable to export the evaluation result to.
                                // scope - The scope for the var. 'application', 'session', 'request' and 'page' scopes are supported. Defaults to page scope. This attribute has no effect unless the var attribute is also defined.
                                // htmlEscape - Set HTML escaping for this tag, as a boolean value. Overrides the default HTML escaping setting for the current page.
                                // javaScriptEscape - Set JavaScript escaping for this tag, as a boolean value. Default is 'false'.
                                .withNewName("span", XMLNS)
                                //.removesAtributes(#quotedList($tag.attributes))
                                .addsAttributes(newAttribute("text", TH)
                                        .withValue(chooseFormat("${%{expression}}"))//,
                                        //      .withNewTextContent("%{value!humanReadable}"),
                                        ,
                                         newAttributeTH("remove")
                                                .withValue(constant("tag"))
                                ));

        AvailableConverters.addConverter("http://www.springframework.org/tags", springTaglibConverterSource);
    }

}
