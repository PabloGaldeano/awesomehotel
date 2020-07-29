package com.tss.awesomehotel.dao.customer.redis;


import com.tss.awesomehotel.dao.customer.CustomerTokenDAO;
import com.tss.awesomehotel.dao.customer.redis.repositories.RedisCustomerTokenRepository;
import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.customer.CustomerToken;
import com.tss.awesomehotel.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Class used to keep the tokens of the customers in redis.
 * In this case, each key will be customer ID and the value the
 * generated token by the system.
 */
@Repository("RedisCustomerToken")
public class RedisCustomerTokenDAO implements CustomerTokenDAO
{
    /**
     * Reference to the actual connection to the service
     */
    @Autowired
    private RedisCustomerTokenRepository redisRepository;

    /**
     * Retrieves a customer token by it's ke (ID)
     *
     * @param customerID The ID of the customer
     * @return An optional container with the customer token
     */
    @Override
    public Optional<CustomerToken> getTokenForCustomer(@NonNull String customerID)
    {
        return this.redisRepository.findById(customerID);
    }

    /**
     * Return the token for the customer object
     *
     * @param customer The customer object to retrieve the token for
     * @return Same as {@link #getTokenForCustomer(String)}
     */
    @Override
    public Optional<CustomerToken> getTokenForCustomer(@NonNull Customer customer)
    {
        return this.getTokenForCustomer(customer.getCustomerID());
    }

    /**
     * Method to save the customer token in redis
     *
     * @param customerID The ID of the customer
     * @param token      The token
     * @return <code>true</code> if was inserted successfully <code>false</code> otherwise
     */
    @Override
    public boolean saveCustomerToken(@NonNull String customerID, @NonNull String token)
    {
        if (StringHelper.checkIfStringContainsSomething(customerID) && StringHelper.checkIfStringContainsSomething(token))
        {
            CustomerToken customerToken = new CustomerToken(customerID, token);
            return this.redisRepository.save(customerToken) != null;
        } else
        {
            throw new IllegalArgumentException("Both the customer ID and the token must contain valid values");
        }
    }

    /**
     * Same as {@link #saveCustomerToken(String, String)} but in this case, it recieves a customer object.
     *
     * @param customer The customer object to get the ID from
     * @param token The token value
     * @return Same as {{@link #saveCustomerToken(String, String)}}
     */
    @Override
    public boolean saveCustomerToken(@NonNull Customer customer, String token)
    {
        return this.saveCustomerToken(customer.getCustomerID(), token);
    }
}
