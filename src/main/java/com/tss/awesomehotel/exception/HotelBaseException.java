package com.tss.awesomehotel.exception;

import com.tss.awesomehotel.utils.logging.HotelLog;

import java.util.logging.Level;

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
