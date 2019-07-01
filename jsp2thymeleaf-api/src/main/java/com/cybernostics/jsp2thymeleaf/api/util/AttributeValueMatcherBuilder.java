package com.cybernostics.jsp2thymeleaf.api.util;

import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author jason
 */
public class AttributeValueMatcherBuilder
{

    public static AttributeValueMatcherBuilder attribute(String attributeName)
    {
        return new AttributeValueMatcherBuilder(attributeName);
    }

    private final String attributeName;
    private String attributeValue;

    private AttributeValueMatcherBuilder(String attributeName)
    {
        this.attributeName = attributeName;
    }

    public AttributeValueMatcherBuilder hasValue(String expectedValue)
    {
        attributeValue = expectedValue;
        return this;
    }

    public AlternateFormatStrings.FormatCondition chooseFormats(Function<Map<String, Object>, String> formatter)
    {
        return new AlternateFormatStrings.FormatCondition(m -> m.getOrDefault(attributeName, "__xxxNOTHERExxx___").equals(attributeValue), formatter);
    }

    public AlternateFormatStrings.FormatCondition chooseFormats(String... formats)
    {
        return chooseFormats(AlternateFormatStrings.chooseFormat(formats));
    }

}
