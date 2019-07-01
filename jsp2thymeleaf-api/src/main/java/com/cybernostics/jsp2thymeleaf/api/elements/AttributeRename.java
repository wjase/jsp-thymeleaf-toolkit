/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.elements;

/**
 *
 * @author jason
 */
public class AttributeRename {
  
  private String from;
  private String to;

  private AttributeRename(String from, String to) {
    this.from = from;
    this.to = to;
  }
  
  public static AttributeRenameBuilder from(String fromStr){
    return new AttributeRenameBuilder(fromStr);
  }
  
  public static class AttributeRenameBuilder{
    String from;

    private AttributeRenameBuilder(String from) {
      this.from = from;
    }
    
    public AttributeRename to(String to){
      return new AttributeRename(from,to);
    }
    
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }
  
  
  
}
