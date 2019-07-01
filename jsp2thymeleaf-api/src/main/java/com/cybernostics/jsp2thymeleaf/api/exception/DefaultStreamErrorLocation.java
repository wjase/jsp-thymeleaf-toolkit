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
public class DefaultStreamErrorLocation implements StreamErrorLocation
{

    private int line;
    private int column;

    public DefaultStreamErrorLocation(int line, int column)
    {
        this.line = line;
        this.column = column;
    }

    @Override
    public int getLine()
    {
        return line;
    }

    @Override
    public int getColumn()
    {
        return column;
    }

}
