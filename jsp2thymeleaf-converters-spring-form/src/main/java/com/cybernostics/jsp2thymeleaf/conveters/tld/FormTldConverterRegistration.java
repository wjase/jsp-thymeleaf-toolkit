package com.cybernostics.jsp2thymeleaf.conveters.tld;

import com.cybernostics.jsp2thymeleaf.api.common.AvailableConverters;
import static com.cybernostics.jsp2thymeleaf.api.common.Namespaces.TH;
import static com.cybernostics.jsp2thymeleaf.api.common.Namespaces.XMLNS;
import com.cybernostics.jsp2thymeleaf.api.common.taglib.ConverterRegistration;
import static com.cybernostics.jsp2thymeleaf.api.elements.JspTagElementConverter.converterFor;
import com.cybernostics.jsp2thymeleaf.api.elements.TagConverterSource;
import static com.cybernostics.jsp2thymeleaf.api.elements.ContentCreator.element;
import static com.cybernostics.jsp2thymeleaf.api.elements.ContentCreator.text;
import static com.cybernostics.jsp2thymeleaf.api.util.AlternateFormatStrings.chooseFormat;
import static com.cybernostics.jsp2thymeleaf.api.elements.AttributeCreator.newAttribute;
import static com.cybernostics.jsp2thymeleaf.api.elements.AttributeCreator.newAttributeTH;
import static com.cybernostics.jsp2thymeleaf.api.elements.AttributeCreator.pairOf;
import static com.cybernostics.jsp2thymeleaf.api.elements.AttributeRename.from;
import com.cybernostics.jsp2thymeleaf.api.elements.JspTagElementConverter;

/**
 *
 * @author jason
 */
public class FormTldConverterRegistration implements ConverterRegistration {


  @Override
  public void run() {
    final TagConverterSource formTaglibConverterSource = new TagConverterSource()
      .withConverters(/* Renders an HTML 'form' tag and exposes a binding path to inner tags
		for binding.  */
        converterFor("form")
          .that(this::convertsCssAttributes)
          .withNamespace(XMLNS)
          // htmlEscape - Enable/disable HTML escaping of rendered values.
          .removesAttributes("methodParam","commandName")
          .renamesAttribute("commandName", "object", TH) //.removesAtributes(#quotedList($tag.attributes))
          .renamesAttribute("modelAttribute", "object", TH) //.removesAtributes(#quotedList($tag.attributes))
        ,
         converterFor("input")
          .that(this::convertsCssAttributes)
          .stripsNamespace()
          .removesAttributes("path", "label")
          .addsAttributes(
            newAttributeTH("field")
              .ignoreBlank()
              .withValue(chooseFormat("*{%{path}}"))
          )
          // autocomplete - Common Optional Attribute
          .appendsContent(element("label")
            .enabledWhenAttributePresent("label")
            .withNamespace(XMLNS)
            .withAttributes(newAttributeTH("for")
              .ignoreBlank()
              .withValue(chooseFormat("#ids.prev('%{path!trimQuotes}')}"))
            )
            .withContent(
              text()
                .withValue(chooseFormat("%{label}", "")))
          ),
        converterFor("password", "hidden", "radiobutton", "checkbox")
          .that(this::convertsCssAttributes)
          .withNewName("input")
          .stripsNamespace()
          .removesAttributes("path", "label")
          .addsAttributes(
            newAttributeTH("field")
              .ignoreBlank()
              .withValue(chooseFormat("*{%{path}}")),
            newAttribute("type")
              .withValue("%{__tagname__}")
          )
          .appendsContent(
            element("label")
            .enabledWhenAttributePresent("label")
            .withNamespace(XMLNS)
            .withAttributes(newAttributeTH("for")
              .ignoreBlank()
              .withValue(chooseFormat("#ids.prev('%{path!trimQuotes}')"))
            )
            .withContent(
              text()
                .withValue(chooseFormat("%{label!trimQuotes}", "")))
          ),
        converterFor("select")
          .that(this::convertsCssAttributes)
          .withNewName("select") //.removesAtributes(#quotedList($tag.attributes))
          .stripsNamespace()
          .renamesAttribute("path", "field", TH) /**
         * <select th:field="*{country}">
         * <option value="0" label="Select" />
         * <option th:each="item: ${countryList}" th:value="${item.countryId}" th:label="${item.countryName}" />
         * </select>
         */
        // attributes
        // path - Path to property for data binding
        // htmlEscape - Enable/disable HTML escaping of rendered values.
        // cssClass - Equivalent to "class" - HTML Optional Attribute
        // cssErrorClass - Equivalent to "class" - HTML Optional Attribute. Used when the bound field has errors.
        // cssStyle - Equivalent to "style" - HTML Optional Attribute
        ,
        /* Renders a single HTML 'option'.
		Sets 'selected' as appropriate based on bound value.  */
         converterFor("option")
          // attributes
          // id - HTML Standard Attribute
          // htmlEscape - Enable/disable HTML escaping of rendered values.
          // cssClass - Equivalent to "class" - HTML Optional Attribute
          // cssErrorClass - Equivalent to "class" - HTML Optional Attribute. Used when the bound field has errors.
          // cssStyle - Equivalent to "style" - HTML Optional Attribute
          .withNewName("option") //.removesAtributes(#quotedList($tag.attributes))
           .stripsNamespace()        //.addsAttributes(NewAttributeBuilder.attributeNamed("object", TH)
        //      .withValue("chooseFormat("${%{commandName}}"))//,
        //      .withNewTextContent("%{value!humanReadable}"),
        ,
        /* Renders a list of HTML 'option' tags. Sets 'selected' as appropriate
		based on bound value.  */
         converterFor("options")
          .withNewName("option", XMLNS)
          .addsAttributes(
            newAttributeTH("each")
              .withValue(chooseFormat("eachOptionItem : %{items}")),
            newAttributeTH("label")
              .withValue(chooseFormat("${eachOptionItem.%{itemValue}}")))
          .removesAttributes("items", "itemValue", "itemLabel") //.addsAttributes(NewAttributeBuilder.attributeNamed("object", TH)
        // attributes
        // items - The Collection, Map or array of objects used to generate the inner 'option' tags. This attribute is required unless the containing select's property for data binding is an Enum, in which case the enum's values are used.
        // itemValue - Name of the property mapped to 'value' attribute of the 'option' tag
        // itemLabel - Name of the property mapped to the inner text of the 'option' tag
        // htmlEscape - Enable/disable HTML escaping of rendered values.
        ,
        /* Renders multiple HTML 'input' tags with type 'checkbox'.  
                         <ul>
          <li th:each="feat : ${allFeatures}">
            <input type="checkbox"  th:value="${feat}" />
            <label th:for="${#ids.prev('features')}" 
                   th:text="#{${'seedstarter.feature.' + feat}}">Heating</label>
          </li>
</ul>
                         
         */repeatedElementConverter("checkboxes"),
        repeatedElementConverter("radiobuttons"),

        // attributes
        // path - Path to property for data binding
        // id - HTML Standard Attribute
        // htmlEscape - Enable/disable HTML escaping of rendered values.
        // cssClass - Equivalent to "class" - HTML Optional Attribute
        // cssErrorClass - Equivalent to "class" - HTML Optional Attribute. Used when the bound field has errors.
        // cssStyle - Equivalent to "style" - HTML Optional Attribute
        // lang - HTML Standard Attribute
        // title - HTML Standard Attribute
        // dir - HTML Standard Attribute
        // tabindex - HTML Standard Attribute
        // disabled - HTML Optional Attribute. Setting the value of this attribute to 'true' will disable the HTML element.
        // onclick - HTML Event Attribute
        // ondblclick - HTML Event Attribute
        // onmousedown - HTML Event Attribute
        // onmouseup - HTML Event Attribute
        // onmouseover - HTML Event Attribute
        // onmousemove - HTML Event Attribute
        // onmouseout - HTML Event Attribute
        // onkeypress - HTML Event Attribute
        // onkeyup - HTML Event Attribute
        // onkeydown - HTML Event Attribute
        // onfocus - HTML Event Attribute
        // onblur - HTML Event Attribute
        // onchange - HTML Event Attribute
        // accesskey - HTML Standard Attribute
        // items - The Collection, Map or array of objects used to generate the 'input' tags with type 'checkbox'
        // itemValue - Name of the property mapped to 'value' attribute of the 'input' tags with type 'checkbox'
        // itemLabel - Value to be displayed as part of the 'input' tags with type 'checkbox'
        // delimiter - Delimiter to use between each 'input' tag with type 'checkbox'. There is no delimiter by default.
        // element - Specifies the HTML element that is used to enclose each 'input' tag with type 'checkbox'. Defaults to 'span'.

        /* Renders an HTML 'textarea'.  */
        converterFor("textarea")
          .that(this::convertsCssAttributes)
          .stripsNamespace() // attributes
          .removesAttributes("path", "label")
          .addsAttributes(
            newAttributeTH("field")
              .ignoreBlank()
              .withValue(chooseFormat("*{%{path}}")))
        // path - Path to property for data binding
        // id - HTML Standard Attribute
        // htmlEscape - Enable/disable HTML escaping of rendered values.
        // cssClass - Equivalent to "class" - HTML Optional Attribute
        // cssErrorClass - Equivalent to "class" - HTML Optional Attribute. Used when the bound field has errors.
        // cssStyle - Equivalent to "style" - HTML Optional Attribute
        // lang - HTML Standard Attribute
        // title - HTML Standard Attribute
        // dir - HTML Standard Attribute
        // tabindex - HTML Standard Attribute
        // disabled - HTML Optional Attribute. Setting the value of this attribute to 'true' will disable the HTML element.
        // onclick - HTML Event Attribute
        // ondblclick - HTML Event Attribute
        // onmousedown - HTML Event Attribute
        // onmouseup - HTML Event Attribute
        // onmouseover - HTML Event Attribute
        // onmousemove - HTML Event Attribute
        // onmouseout - HTML Event Attribute
        // onkeypress - HTML Event Attribute
        // onkeyup - HTML Event Attribute
        // onkeydown - HTML Event Attribute
        // onfocus - HTML Event Attribute
        // onblur - HTML Event Attribute
        // onchange - HTML Event Attribute
        // accesskey - HTML Standard Attribute
        // rows - HTML Required Attribute
        // cols - HTML Required Attribute
        // onselect - HTML Event Attribute
        // readonly - HTML Optional Attribute. Setting the value of this attribute to 'true' will make the HTML element readonly.
        //.addsAttributes(NewAttributeBuilder.attributeNamed("object", TH)
        //      .withValue("chooseFormat("${%{commandName}}"))//,
        //      .withNewTextContent("%{value!humanReadable}"),
        ,
        /* Renders field errors in an HTML 'span' tag.  */
        /*
        <ul th:if="${#fields.hasErrors('*')}">
        <li th:each="err : ${#fields.errors('*')}" th:text="${err}">Input is incorrect</li>
        </ul>
        */
         converterFor("errors")
           .replaceWith(
            element("ul")
              .withAttributes(
                newAttributeTH("if").withValue("${#fields.hasErrors('*')}"),
                newAttribute("style").withValue("%{cssStyle|}"),
                newAttribute("class").withValue("%{cssClass|}")
              )
              
              .withContent(
                element("li")
                .withAttributes(
                  newAttributeTH("each").withValue("err : ${#fields.errors('*')}"),
                  newAttributeTH("text").withValue("${err}"))
              )
           )
          // attributes
          // path - Path to errors object for data binding
          // id - HTML Standard Attribute
          // htmlEscape - Enable/disable HTML escaping of rendered values.
          // delimiter - Delimiter for displaying multiple error messages. Defaults to the HTML br tag.
          // cssClass - Equivalent to "class" - HTML Optional Attribute
          // cssStyle - Equivalent to "style" - HTML Optional Attribute
          // lang - HTML Standard Attribute
          // title - HTML Standard Attribute
          // dir - HTML Standard Attribute
          // tabindex - HTML Standard Attribute
          // onclick - HTML Event Attribute
          // ondblclick - HTML Event Attribute
          // onmousedown - HTML Event Attribute
          // onmouseup - HTML Event Attribute
          // onmouseover - HTML Event Attribute
          // onmousemove - HTML Event Attribute
          // onmouseout - HTML Event Attribute
          // onkeypress - HTML Event Attribute
          // onkeyup - HTML Event Attribute
          // onkeydown - HTML Event Attribute
          // element - Specifies the HTML element that is used to render the enclosing errors.
        //.addsAttributes(NewAttributeBuilder.attributeNamed("object", TH)
        //      .withValue("chooseFormat("${%{commandName}}"))//,
        //      .withNewTextContent("%{value!humanReadable}"),
        ,
        /* Renders a form field label in an HTML 'label' tag.  */
         converterFor("label")
           .that(this::convertsCssAttributes)
          // attributes
          // path - Path to property for data binding
          // id - HTML Standard Attribute
          // htmlEscape - Enable/disable HTML escaping of rendered values.
          // for - HTML Standard Attribute
          // cssClass - Equivalent to "class" - HTML Optional Attribute.
          // cssErrorClass - Equivalent to "class" - HTML Optional Attribute. Used only when errors are present.
          // cssStyle - Equivalent to "style" - HTML Optional Attribute
          // lang - HTML Standard Attribute
          // title - HTML Standard Attribute
          // dir - HTML Standard Attribute
          // tabindex - HTML Standard Attribute
          // onclick - HTML Event Attribute
          // ondblclick - HTML Event Attribute
          // onmousedown - HTML Event Attribute
          // onmouseup - HTML Event Attribute
          // onmouseover - HTML Event Attribute
          // onmousemove - HTML Event Attribute
          // onmouseout - HTML Event Attribute
          // onkeypress - HTML Event Attribute
          // onkeyup - HTML Event Attribute
          // onkeydown - HTML Event Attribute
          .withNewName("label", XMLNS) //.removesAtributes(#quotedList($tag.attributes))
        //.addsAttributes(NewAttributeBuilder.attributeNamed("object", TH)
        //      .withValue("chooseFormat("${%{commandName}}"))//,
        //      .withNewTextContent("%{value!humanReadable}"),
        ,
        /* Renders an HTML 'button' tag.  */
         converterFor("button")
          // attributes
          // id - HTML Standard Attribute
          // name - The name attribute for the HTML button tag
          // value - The value attribute for the HTML button tag
          // disabled - HTML Optional Attribute. Setting the value of this attribute to 'true' will disable the HTML element.
          .withNewName("button", XMLNS) //.removesAtributes(#quotedList($tag.attributes))
      );
    AvailableConverters.addConverter("http://www.springframework.org/tags/form", formTaglibConverterSource);
  }

  JspTagElementConverter convertsCssAttributes(JspTagElementConverter converter){
    converter.renamesAttributes(from("cssClass").to("class"));
    converter.renamesAttributes(from("cssStyle").to("style"));
    converter.addsAttributes(
      newAttributeTH("errorClass")
        .ignoreBlank().withValue("%{cssErrorClass}")
      );
    return converter;
  }
  JspTagElementConverter repeatedElementConverter(String pluralTagName) {
    String singleTagName = trimTrailingSChar(pluralTagName);
    String itemName = singleTagName + "Item";
    return converterFor(pluralTagName)
      .that(this::convertsCssAttributes)
      .replaceWith(
        element("ul")
          .withContent(
            element("li")
              .withAttributes(
                newAttributeTH("each")
                  .withValue(itemName + " : ${%{items!stripAll}}")
              ).withContent(
                element("input")
                  .withAttributes(
                    pairOf("type", singleTagName),
                    pairOf("name", "%{items!stripAll}"),
                    newAttributeTH("value").withValue("${" + itemName + "}"),
                    pairOf("id", "#ids.seq('%{items!stripAll}')")),
                element("label")
                  .withAttributes(
                    newAttributeTH("text").withValue("${" + itemName + ".%{itemLabel|noLabel!stripAll}}"),
                    newAttributeTH("for").withValue("#ids.prev('%{items!stripAll}')")
                  )
              )
          )
      );
  }

  private String trimTrailingSChar(String tagname) {
    return tagname.replaceAll("e?s$", "");
  }

}
