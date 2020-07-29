package com.tss.awesomehotel.dao.customer;

import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.customer.CustomerToken;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface CustomerTokenDAO
{
    /**
     * Retrieves a customer token by it's ke (ID)
     *
     * @param customerID The ID of the customer
     * @return An optional container with the customer token
     */
    public Optional<CustomerToken> getTokenForCustomer(@NonNull String customerID);

    /**
     * Return the token for the customer object
     *
     * @param customer The customer object to retrieve the token for
     * @return Same as {@link #getTokenForCustomer(String)}
     */
    public Optional<CustomerToken> getTokenForCustomer(@NonNull Customer customer);

    /**
     * Method to save the customer token in redis
     *
     * @param customerID The ID of the customer
     * @param token      The token
     * @return <code>true</code> if was inserted successfully <code>false</code> otherwise
     */
    public boolean saveCustomerToken(@NonNull String customerID, @NonNull String token);

    /**
     * Same as {@link #saveCustomerToken(String, String)} but in this case, it recieves a customer object.
     *
     * @param customer The customer object to get the ID from
     * @param token    The token value
     * @return Same as {{@link #saveCustomerToken(String, String)}}
     */
    public boolean saveCustomerToken(@NonNull Customer customer, @NonNull String token);

}
