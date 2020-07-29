package com.tss.awesomehotel.exception;

import com.tss.awesomehotel.utils.logging.HotelLog;

import java.util.logging.Level;

/**
 * This exception logs itself into a file
 */
public class HotelLoggeableException extends HotelBaseException
{
    /**
     * A reference of the causer of this exception
     */
    private Throwable cause;

    public HotelLoggeableException(int exceptionCode, String message)
    {
        super(exceptionCode, message);
        this.logException();
    }

    public HotelLoggeableException(int exceptionCode, String message, Throwable cause)
    {
        super(exceptionCode, message);
        this.cause = cause;
        this.logException();
    }

    /**
     * This methods logs the exceptions into the appropriate file, if there is a cause it will
     * log it as well.
     */
    private void logException()
    {
        if (this.cause == null)
        {
            HotelLog.logMessageInFile("Exceptions", Level.WARNING, this.getMessage());
        }
        else
        {
            HotelLog.logComposedMessageInFile("Exceptions",Level.SEVERE, this.getMessage(), this.cause);
        }
    }
}
