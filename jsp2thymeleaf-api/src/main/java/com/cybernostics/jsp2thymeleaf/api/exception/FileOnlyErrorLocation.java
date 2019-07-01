/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.exception;

/**
 *
 * @author jason
 */
public class FileOnlyErrorLocation implements FileErrorLocation
{

    private String filename;

    public FileOnlyErrorLocation(String filename)
    {
        this.filename = filename;
    }

    @Override
    public String getFilename()
    {
        return filename;
    }

    @Override
    public int getLine()
    {
        return 0;
    }

    @Override
    public int getColumn()
    {
        return 0;
    }

    @Override
    public String toString()
    {
        return filename;
    }

}
