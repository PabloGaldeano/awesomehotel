package com.tss.awesomehotel.exception;

import com.tss.awesomehotel.exception.codes.ErrorCodes;

public class InternalHotelException extends HotelLoggeableException
{

    public InternalHotelException(String message)
    {
        super(ErrorCodes.INTERNAL.getErrorCode(), String.format(ErrorCodes.INTERNAL.getErrorMessage(), message));
    }
}
