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
public class JSP2ThymeLeafException extends RuntimeException implements MutableFileLocation, HasLocationInStream
{

    private HasErrorLocation fileErrorLocation;
    private TokenisedFile file;

    @Override
    public void setFile(TokenisedFile file)
    {
        this.file = file;
    }

    public static class JSP2ThymeLeafExceptionBuilder
    {

        private final JSP2ThymeLeafException exc;

        public JSP2ThymeLeafExceptionBuilder(String message, Throwable cause)
        {
            exc = new JSP2ThymeLeafException(message, cause);
            withLocationSource(locationSource(cause));
        }

        public JSP2ThymeLeafExceptionBuilder(Throwable cause)
        {
            exc = new JSP2ThymeLeafException(cause);
            withLocationSource(locationSource(cause));
        }

        public JSP2ThymeLeafExceptionBuilder(String message)
        {
            exc = new JSP2ThymeLeafException(message);
            withLocationSource(locationSource(null));
        }

        public JSP2ThymeLeafExceptionBuilder withLocationSource(HasErrorLocation locationSource)
        {
            exc.fileErrorLocation = locationSource;
            return this;
        }

        public JSP2ThymeLeafException build()
        {
            return exc;
        }
    }

    public static JSP2ThymeLeafExceptionBuilder jsp2ThymeLeafExceptionBuilder(String message, Throwable cause)
    {
        return new JSP2ThymeLeafExceptionBuilder(message, cause);
    }

    public static JSP2ThymeLeafExceptionBuilder jsp2ThymeLeafExceptionBuilder(String message)
    {
        return new JSP2ThymeLeafExceptionBuilder(message);
    }

    public static JSP2ThymeLeafExceptionBuilder jsp2ThymeLeafExceptionBuilder(Throwable cause)
    {
        return new JSP2ThymeLeafExceptionBuilder(cause);
    }

    protected JSP2ThymeLeafException(String message)
    {
        super(message);
    }

    protected JSP2ThymeLeafException(String message, Throwable cause)
    {
        super(message, cause);
    }

    protected JSP2ThymeLeafException(Throwable cause)
    {
        super(cause);
    }

    @Override
    public FileErrorLocation getLocation()
    {
        if (fileErrorLocation == null)
        {
            return locationSource(getCause()).getLocation();
        }
        return fileErrorLocation.getLocation();
    }

    public static HasErrorLocation locationSource(final Throwable cause)
    {
        if (cause instanceof HasErrorLocation)
        {
            HasErrorLocation errorLocation = (HasErrorLocation) cause;
            return errorLocation;
        }
        return () -> new DefaultFileErrorLocation("UNKNOWN", 0, 0);
    }

    protected void setLocationSource(HasErrorLocation loc)
    {
        fileErrorLocation = loc;
    }
}
