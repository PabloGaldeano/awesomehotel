package com.tss.awesomehotel.model.customer;

import com.tss.awesomehotel.utils.StringHelper;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("CustomerToken")
public class CustomerToken implements Serializable
{
    @Id
    private String customerID;
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
