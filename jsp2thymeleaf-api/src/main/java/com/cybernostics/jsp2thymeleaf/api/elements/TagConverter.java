package com.cybernostics.jsp2thymeleaf.api.elements;

/**
 *
 * @author jason
 */
public interface TagConverter extends JSPElementNodeConverter
{
    /**
     * Returns the name of the tag this converter converts eg c:out
     * @return 
     */
    public String getApplicableTag();
    

}
