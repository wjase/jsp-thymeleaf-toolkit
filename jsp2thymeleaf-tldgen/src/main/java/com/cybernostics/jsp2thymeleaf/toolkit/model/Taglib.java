/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.toolkit.model;

/*-
 * #%L
 * jsp2thymeleaf-tldgen
 * %%
 * Copyright (C) 1992 - 2017 Cybernostics
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author jason
 */
public class Taglib
{

    public static Taglib parse(InputStream tldStream)
    {
        try
        {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(tldStream);
            Element tagllibNode = document.getRootElement();
            Namespace ns = tagllibNode.getNamespace();
            Taglib taglib = new Taglib(tagllibNode.getChild("short-name", ns).getText(),
                    tagllibNode.getChild("uri", ns).getText(),
                    tagllibNode.getChild("description", ns).getText());
            taglib.addAll((List<Tag>) tagllibNode.getChildren("tag", ns)
                    .stream()
                    .map(Taglib::tagForElement)
                    .collect(toList()));
            return taglib;

        } catch (Exception ex)
        {
            Logger.getLogger(Taglib.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Tag tagForElement(Object el)
    {
        Element tagElement = (Element) el;
        Namespace ns = tagElement.getNamespace();
        final Tag tag = new Tag(tagElement.getChildText("name", ns), tagElement.getChildText("description", ns));
        List<Element> attributes = tagElement.getChildren("attribute", ns);
        tag.addAll((List<Attribute>) attributes.stream()
                .map(Taglib::attributeForTag)
                .collect(toList()));
        return tag;

    }

    public static Attribute attributeForTag(Object el)
    {
        Element tagElement = (Element) el;
        Namespace ns = tagElement.getNamespace();
        return new Attribute(tagElement.getChildText("name", ns), tagElement.getChildText("description", ns));

    }

    public Taglib(String name, String uri, String description)
    {
        this.name = name;
        this.uri = uri;
        this.description = description;
    }

    public void addAll(List<Tag> tags)
    {
        this.tags.addAll(tags);
    }

    private String name;
    private String uri;
    private String description;
    private List<Tag> tags = new ArrayList<>();

    public void add(Tag tag)
    {
        this.tags.add(tag);
    }

    public String getUri()
    {
        return uri;
    }

    public String getDescription()
    {
        return description;
    }

    public List<Tag> getTags()
    {
        return tags;
    }

    public String getName()
    {
        return name;
    }

    public Taglib(String name)
    {
        this.name = name;
    }

    public static class Tag
    {

        private String name;
        private final String description;
        private List<Attribute> attributes = new ArrayList<>();

        public String getDescription()
        {
            return description;
        }

        public Tag(String name, String description)
        {
            this.name = name;
            this.description = description;
        }

        public String getName()
        {
            return name;
        }

        public void add(Attribute attribute)
        {
            this.attributes.add(attribute);
        }

        public void addAll(List<Attribute> attributes)
        {
            this.attributes.addAll(attributes);
        }

        public List<Attribute> getAttributes()
        {
            return attributes;
        }
    }

    public static class Attribute
    {

        private String name;
        private String description;

        public Attribute(String name, String description)
        {
            this.name = name;
            this.description = description.replaceAll("\\s*\n\\s*", " ");
        }

        public String getName()
        {
            return name;
        }

        public String getDescription()
        {
            return description;
        }
    }

}
