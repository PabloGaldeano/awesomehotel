package com.tss.awesomehotel.model.interfaces;

import com.tss.awesomehotel.model.customer.CustomerToken;

/**
 * This method is used by the implementing clasess to convert
 * a token on its string form to a {@link CustomerToken}
 */
public interface TokenStringParser
{
    /**
     * Method used to parse the token and convert it to a {@link CustomerToken}
     *
     * @param token The token to parse
     * @return A instance of {@link CustomerToken} or null.
     */
    CustomerToken convertToken(String token);
}
