/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.elements;

import com.cybernostics.jsp2thymeleaf.api.common.Namespaces;
import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_SET;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import static org.apache.commons.collections.ListUtils.EMPTY_LIST;
import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.Text;

/**
 *
 * @author jason
 */
public interface ContentCreator extends Function<Map<String, Object>, List<Content>> {

    public static DefaultElementCreator element(String name) {
        return new DefaultElementCreator(name);
    }

    public static DefaultTextCreator text() {
        return new DefaultTextCreator();
    }
    
    public static class DefaultTextCreator implements ContentCreator{

        private Function<Map<String, Object>, String> contentCreator;
        
        public DefaultTextCreator withValue(Function<Map<String, Object>, String> strings) {
            this.contentCreator = strings;
            return this;
        }
        
        @Override
        public List<Content> apply(Map<String, Object> attributes) {
            return asList(new Text(contentCreator.apply(attributes)));
        }
        
    }

    public static class DefaultElementCreator implements ContentCreator {

        private String name;
        private Namespace namespace = Namespaces.XMLNS;
        private Set<AttributeCreator> attributes=EMPTY_SET;
        private Function<Map< String, Object>, Boolean> enabledWhen = (map) -> {
            return true;
        };
        private List<ContentCreator> childContentBuilders;

        public DefaultElementCreator(String name) {
            ContentCreator c = (m) -> EMPTY_LIST;
            this.childContentBuilders = asList(c);
            this.name = name;
        }

        public DefaultElementCreator withNamespace(Namespace namespace) {
            this.namespace = namespace;
            return this;
        }

        public DefaultElementCreator withAttributes(AttributeCreator... builders) {
            attributes = Stream.of(builders).collect(toSet());
            return this;
        }
        
        public DefaultElementCreator enabledWhen(Function<Map< String, Object>, Boolean> enabled){
            this.enabledWhen = enabled;
            return this;
        }
        
        public DefaultElementCreator enabledWhenAttributePresent(String attributeName){
            this.enabledWhen = (m)->m.containsKey(attributeName);
            return this;
        }

        @Override
        public List<Content> apply(Map<String, Object> attributeMap) {
            if (enabledWhen.apply(attributeMap)) {
                Element result = new Element(name, namespace);
                attributes
                        .stream()
                        .flatMap(builder -> builder.buildNewAttributes(attributeMap).stream())
                        .forEach(attribute -> result.setAttribute(attribute));
                List<Content> content = childContentBuilders
                        .stream()
                        .flatMap(b->b.apply(attributeMap).stream())
                        .collect(toList());
                
                if (content != null) {
                    result.addContent(content);
                }
                return asList(result);
            }
            return EMPTY_LIST;
        }

        public DefaultElementCreator withContent(ContentCreator ... builders) {
            this.childContentBuilders = asList(builders);
            return this;
        }

        public DefaultElementCreator forAttribute(String label) {
            enabledWhen = (map) -> {
                return map.containsKey(map);
            };
            return this;
        }

    }

}
