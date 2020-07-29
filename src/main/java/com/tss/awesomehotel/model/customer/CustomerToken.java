package com.tss.awesomehotel.model.customer;

import com.tss.awesomehotel.utils.StringHelper;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * This is the model of the customer token.
 */
@RedisHash("CustomerToken")
public class CustomerToken implements Serializable
{
    /**
     * The ID of the customer
     */
    @Id
    private String customerID;

    /**
     * The token value
     */
    private String token;

    public CustomerToken(String customerID, String token)
    {
        this.customerID = customerID;
        this.token = token;
    }

    public String getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(String customerID)
    {
        this.customerID = customerID;
    }

    public String getToken()
    {
        return this.token;
    }



}
