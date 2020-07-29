package com.tss.awesomehotel.exception;


/**
 * The base exception used across the system.
 */
public class HotelBaseException extends Exception
{
    private int exceptionCode;

    public HotelBaseException(int exceptionCode, String message)
    {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public int getExceptionCode()
    {
        return exceptionCode;
    }

    @Override
    public String toString()
    {
        return String.format("An exception occurred! With code %d and message %s", this.exceptionCode, this.getMessage());
    }
}
