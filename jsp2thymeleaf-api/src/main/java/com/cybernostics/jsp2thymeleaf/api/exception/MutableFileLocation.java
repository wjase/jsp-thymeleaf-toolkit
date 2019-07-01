/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.exception;

import com.cybernostics.jsp2thymeleaf.api.common.TokenisedFile;

/**
 *
 * @author jason
 */
public interface MutableFileLocation
{

    void setFile(TokenisedFile file);
}
