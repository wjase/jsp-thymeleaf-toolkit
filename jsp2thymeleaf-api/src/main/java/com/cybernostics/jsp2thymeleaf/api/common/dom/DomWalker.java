/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.common.dom;

import static java.util.Arrays.asList;
import java.util.List;
import java.util.function.Consumer;
import org.jdom2.Content;
import org.jdom2.Element;

/**
 *
 * @author jason
 */
public class DomWalker
{

    private final List<Consumer<Content>> consumers;

    public DomWalker(Consumer<Content>... consumers)
    {
        this.consumers = asList(consumers);
    }

    public void walk(Content toWalk)
    {
        consumers.forEach(action -> action.accept(toWalk));
        if (toWalk instanceof Element)
        {
            ((Element) toWalk).getContent().forEach(child -> walk(child));
        }

    }
}
