package com.tss.awesomehotel.dao.customer;

import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.customer.CustomerToken;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface CustomerTokenDAO
{
    public Optional<CustomerToken> getTokenForCustomer(@NonNull String customerID);

    public Optional<CustomerToken> getTokenForCustomer(@NonNull Customer customer);

    public boolean saveCustomerToken(@NonNull String customerID, @NonNull String token);

    public boolean saveCustomerToken(@NonNull Customer customer, @NonNull String token);

}
