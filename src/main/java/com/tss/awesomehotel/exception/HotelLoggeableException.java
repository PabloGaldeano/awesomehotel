package com.tss.awesomehotel.exception;

import com.tss.awesomehotel.utils.logging.HotelLog;

import java.util.logging.Level;

/**
 * This exception logs itself into a file
 */
public class HotelLoggeableException extends HotelBaseException
{
    public HotelLoggeableException(int exceptionCode, String message)
    {
        super(exceptionCode, message);
        HotelLog.logMessageInFile("Exceptions", Level.WARNING,this.getMessage());
    }
}
