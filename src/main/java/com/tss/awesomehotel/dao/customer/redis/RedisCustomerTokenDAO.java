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

@Repository("RedisCustomerToken")
public class RedisCustomerTokenDAO implements CustomerTokenDAO
{
    @Autowired
    private RedisCustomerTokenRepository redisRepository;

    @Override
    public Optional<CustomerToken> getTokenForCustomer(@NonNull String customerID)
    {
        return this.redisRepository.findById(customerID);
    }

    @Override
    public Optional<CustomerToken> getTokenForCustomer(@NonNull Customer customer)
    {
        return this.getTokenForCustomer(customer.getCustomerID());
    }

    @Override
    public boolean saveCustomerToken(@NonNull String customerID,@NonNull String token)
    {
        boolean ret = false;
        if (StringHelper.checkIfStringContainsSomething(customerID) && StringHelper.checkIfStringContainsSomething(token))
        {
            CustomerToken customerToken = new CustomerToken(customerID, token);
            ret = this.redisRepository.save(customerToken) != null;
        }
        else
        {
            throw new IllegalArgumentException("Both the customer ID and the token must contain valid values");
        }
        return ret;
    }

    @Override
    public boolean saveCustomerToken(@NonNull Customer customer, String token)
    {
        return this.saveCustomerToken(customer.getCustomerID(), token);
    }
}
