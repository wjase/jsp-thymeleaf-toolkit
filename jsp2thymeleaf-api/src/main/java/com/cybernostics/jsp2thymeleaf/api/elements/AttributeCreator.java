/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.elements;

import com.cybernostics.jsp2thymeleaf.api.common.Namespaces;
import static com.cybernostics.jsp2thymeleaf.api.common.Namespaces.TH;
import static com.cybernostics.jsp2thymeleaf.api.common.Namespaces.XMLNS;
import static com.cybernostics.jsp2thymeleaf.api.util.AlternateFormatStrings.chooseFormat;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.jdom2.Attribute;
import org.jdom2.Namespace;

/**
 *
 * @author jason
 */
public interface AttributeCreator {

  List<Attribute> buildNewAttributes(Map<String, Object> currentValues);

  public static class NOPNewAttributeBuilder implements AttributeCreator {

    @Override
    public List<Attribute> buildNewAttributes(Map<String, Object> currentAttributeValues) {
      return Arrays.asList();
    }
  }

  public static DefaultAttributeBuilder newAttribute(String name, Namespace namespace) {
    return new DefaultAttributeBuilder(name, namespace);
  }

  public static DefaultAttributeBuilder newAttribute(String name) {
    return new DefaultAttributeBuilder(name, Namespace.NO_NAMESPACE);
  }

  public static DefaultAttributeBuilder pairOf(String name, String value) {
    return new DefaultAttributeBuilder(name, Namespace.NO_NAMESPACE).withValue(value);
  }

  public static DefaultAttributeBuilder newAttributeTH(String name) {
    return new DefaultAttributeBuilder(name, TH);
  }

  public static class DefaultAttributeBuilder implements AttributeCreator {

    private String name;
    private Namespace namespace = Namespace.NO_NAMESPACE;
    private Function<Map<String, Object>, String> valueMaker;
    private boolean optional = false;

    public DefaultAttributeBuilder(String name, Namespace namespace) {
      this.name = name;
      this.namespace = namespace;
    }

    public DefaultAttributeBuilder withNamespace(Namespace namespace) {
      this.namespace = namespace;
      return this;
    }

    public DefaultAttributeBuilder ignoreBlank(){
      this.optional=true;
      return this;
    }
    
    public DefaultAttributeBuilder withValue(String valueKey) {
      return withValue(chooseFormat(valueKey));
    }

    public DefaultAttributeBuilder withValue(Function<Map<String, Object>, String> valueMaker) {
      this.valueMaker = valueMaker;
      return this;
    }

    @Override
    public List<Attribute> buildNewAttributes(Map<String, Object> currentValues) {
      
      try{
        String value = valueMaker.apply(currentValues);
        return asList(new Attribute(name, value, namespace));
        
      }catch(RuntimeException exception){
        if (!optional) {
          throw exception;
        }
      }
      return Collections.EMPTY_LIST;
    }

  }

}
