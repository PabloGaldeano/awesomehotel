package com.tss.awesomehotel.controller.basic;

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
        }
        catch(IllegalArgumentException illegalArgumentException)
        {
            toReturn = ServiceResponse.createErrorResponse(illegalArgumentException.getMessage());
        }
        return toReturn;
    }

    protected  ServiceResponse handleCallExceptionAndCheckToken(String token, Supplier<ServiceResponse> functionToUse)
    {
        return this.handleCallExceptions(() ->
                (this.tokenService.checkTokenValidity(token)) ? functionToUse.get() :
                        ServiceResponse.createErrorResponse("You have to be logged in"));
    }

    protected Customer getCustomerFromToken(String token)
    {
        Customer tokenOwner = this.tokenService.getCustomerByToken(token);
        if (tokenOwner == null)
        {
            throw new NoSuchElementException("There is no customer with tha token associated");
        }
        return tokenOwner;
    }
}
