package com.tss.awesomehotel.dao.travel;

import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.travel.TravellingCustomers;

import java.util.Optional;

public interface TravelSingUpDAO
{

    /**
     * This method signs a customer for today's ride
     *
     * @param customer The customer to sign up
     * @param placeToGo The ID of the path where the customer is heading
     * @return <code>true</code> if inserted succesfully <code>false</code> otherwise
     */
    public boolean singCustomerInForToday(Customer customer, int placeToGo);

    /**
     * Gets a list of all the customers in today's ride
     * @return The list of customers signed up
     */
    public Optional<TravellingCustomers> getCustomersSignedUpForToday();
}
