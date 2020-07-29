package com.tss.awesomehotel.exception;

import com.tss.awesomehotel.exception.codes.ErrorCodes;

/**
 * Exception type for internal errors
 */
public class HotelInternalException extends HotelLoggeableException
{

    public HotelInternalException(String message)
    {
        super(ErrorCodes.INTERNAL.getErrorCode(), String.format(ErrorCodes.INTERNAL.getErrorMessage(), message));
    }
    
    public HotelInternalException(String message, Throwable cause)
    {
        super(ErrorCodes.INTERNAL.getErrorCode(), String.format(ErrorCodes.INTERNAL.getErrorMessage(), message), cause);

    }
}
