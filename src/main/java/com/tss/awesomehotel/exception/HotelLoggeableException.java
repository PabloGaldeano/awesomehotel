package com.tss.awesomehotel.exception;

import com.tss.awesomehotel.utils.logging.HotelLog;

import java.util.logging.Level;

public class HotelLoggeableException extends HotelBaseException
{
    public HotelLoggeableException(int exceptionCode, String message)
    {
        super(exceptionCode, message);
        HotelLog.logMessageInFile("Errors", Level.WARNING,this.getMessage());
    }
}
