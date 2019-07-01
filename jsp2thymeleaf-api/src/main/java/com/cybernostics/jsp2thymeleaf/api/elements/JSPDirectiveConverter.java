/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.elements;

import com.cybernostics.jsp.parser.JSPParser;
import java.util.List;
import org.jdom2.Content;

/**
 *
 * @author jason
 */
public interface JSPDirectiveConverter
{

    List<Content> process(JSPParser.JspDirectiveContext jspTree, JSPElementNodeConverter context);

    boolean canHandle(JSPParser.JspDirectiveContext jspTree);

}
