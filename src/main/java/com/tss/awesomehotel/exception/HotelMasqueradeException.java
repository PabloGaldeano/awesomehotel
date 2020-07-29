package com.tss.awesomehotel.exception;

import com.tss.awesomehotel.exception.codes.ErrorCodes;

/**
 * This exception is used to hide the internal erros.
 */
public class HotelMasqueradeException extends HotelBaseException
{
    public HotelMasqueradeException()
    {
        super(ErrorCodes.MASQUERADE.getErrorCode(),ErrorCodes.MASQUERADE.getErrorMessage());
    }
}
