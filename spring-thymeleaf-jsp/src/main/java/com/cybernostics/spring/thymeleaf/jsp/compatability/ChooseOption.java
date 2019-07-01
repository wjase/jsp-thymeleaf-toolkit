/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.spring.thymeleaf.jsp.compatability;

import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author jason
 */
public class ChooseOption
{
    public static class Option{
        private String label;
        private Boolean value;

        public Option(String label, Boolean value)
        {
            this.label = label;
            this.value = value;
        }
    }
            
    public static final Option defaultOption = opt("*", Boolean.TRUE);
    
    public static Option opt(String label,Boolean condition){
        return new Option(label, condition);
    }
    
    public static String choose(Option ...options){
        final Optional<Option> selectedOption = Arrays.stream(options)
                .filter((option)->option.value)
                .findFirst();
        return selectedOption.orElse(defaultOption).label;
    }
}
