package com.tss.awesomehotel.controller.basic;

import com.tss.awesomehotel.exception.HotelBaseException;
import com.tss.awesomehotel.exception.HotelInternalException;
import com.tss.awesomehotel.exception.HotelMasqueradeException;
import com.tss.awesomehotel.exception.codes.ErrorCodes;
import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.ServiceResponse;
import com.tss.awesomehotel.service.customer.TokenService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class GenericController
{
    @Autowired
    private TokenService tokenService;


    protected ServiceResponse handleCallExceptions(Supplier<ServiceResponse> functionToUse)
    {
        ServiceResponse toReturn;
        try
        {
            toReturn = functionToUse.get();
        } catch (Exception ex)
        {
           toReturn = this.generateServiceResponseBasedOnExceptionForCommonRequest(ex);
        }
        return toReturn;
    }

    private ServiceResponse generateServiceResponseBasedOnExceptionForCommonRequest(Exception toCheck)
    {
        ServiceResponse toReturn;
        if (toCheck instanceof HotelInternalException)
        {
            toReturn = ServiceResponse.createErrorResponse(ErrorCodes.MASQUERADE.getErrorMessage(),
                    ErrorCodes.MASQUERADE.getErrorCode());
        } else if(toCheck instanceof HotelBaseException)
        {
            toReturn = ServiceResponse.createErrorResponse(toCheck.getMessage(),
                    ((HotelBaseException) toCheck).getExceptionCode());
        }
        else
        {
            toReturn = ServiceResponse.createErrorResponse("Please try again later",0);
        }
        return toReturn;
    }

    protected ServiceResponse handleCallExceptionAndCheckToken(String token, Supplier<ServiceResponse> functionToUse)
    {
        return this.handleCallExceptions(() -> this.doPetitionToServiceWithToken(token, functionToUse));
    }

    protected ServiceResponse doPetitionToServiceWithToken(String token, Supplier<ServiceResponse> functionToUse)
    {
        ServiceResponse toReturn;
        try
        {
           toReturn = this.tokenService.checkTokenValidity(token) ? functionToUse.get() :
            ServiceResponse.fromErrorCode(ErrorCodes.AUTHENTICATION);
        }catch(HotelMasqueradeException exception)
        {
            toReturn = ServiceResponse.fromErrorCode(ErrorCodes.MASQUERADE);
        }
        return toReturn;
    }

    protected Customer getCustomerFromToken(String token)
    {
        try
        {
            return this.tokenService.getCustomerByToken(token);
        }catch (HotelMasqueradeException ex)
        {
            throw new NoSuchElementException("There is no customer with tha token associated");
        }
    }
}
