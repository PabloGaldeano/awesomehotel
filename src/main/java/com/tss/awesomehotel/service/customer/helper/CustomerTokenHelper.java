package com.tss.awesomehotel.service.customer.helper;

import com.tss.awesomehotel.exception.HotelInternalException;
import com.tss.awesomehotel.model.customer.CustomerToken;
import com.tss.awesomehotel.model.interfaces.ModelValidator;
import com.tss.awesomehotel.model.interfaces.TokenStringParser;
import com.tss.awesomehotel.utils.StringHelper;
import org.springframework.beans.factory.annotation.Value;

public class CustomerTokenHelper implements ModelValidator<String>, TokenStringParser
{
    /**
     * The separator used to divide the customer from the token
     */
    @Value("${app.tokens.token_separator}")
    private String tokenSeparator;

    /**
     * The length of the token
     */
    @Value("${app.tokens.token_length}")
    private int tokenLength;


    @Override
    public void validate(String entity)
    {
        if(!(StringHelper.checkIfStringContainsSomething(entity) && entity.indexOf(this.tokenSeparator) > 0))
        {
            throw new IllegalArgumentException("The customer token has to be valid");
        }
    }

    /**
     * Method used to parse the token and convert it to a {@link CustomerToken}
     *
     * @param token The token to parse
     * @return A instance of {@link CustomerToken} or null.
     */
    @Override
    public CustomerToken convertToken(String token)
    {
        try
        {
            this.validate(token);
            String[] tokenParts = token.split(this.tokenSeparator);
            return new CustomerToken(tokenParts[1], tokenParts[0]);
        }catch(IllegalArgumentException ex)
        {
            throw new IllegalArgumentException("The token has to ve valid");
        }
    }
}
