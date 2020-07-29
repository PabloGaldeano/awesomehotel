package com.tss.awesomehotel.controller;

import com.tss.awesomehotel.controller.basic.GenericController;
import com.tss.awesomehotel.model.ServiceResponse;
import com.tss.awesomehotel.service.customer.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is used as a controller to provide an interface
 * in order to operate with the tokens
 */
@RestController
@RequestMapping("/token")
public class TokenController extends GenericController
{
    @Autowired
    private TokenService tokenService;

    /**
     * Method to check if a token is valid or not
     * @param token The token to check
     * @return A response with the operation result
     */
    @GetMapping("/identify/{token}")
    public ServiceResponse identifyCustomer(@NonNull @PathVariable("token") String token)
    {
        return this.handleCallExceptions(() ->
        {
            boolean isTheTokenAlive = this.tokenService.checkTokenValidity(token);
            return (isTheTokenAlive) ? ServiceResponse.createSuccessResponse(isTheTokenAlive) :
                    ServiceResponse.createErrorResponse("The check-in service is unavailable at this time, please retry after a few minutes...");
        });

    }
}
