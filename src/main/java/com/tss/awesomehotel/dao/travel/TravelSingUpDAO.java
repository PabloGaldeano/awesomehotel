package com.tss.awesomehotel.dao.travel;

import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.travel.TravellingCustomers;

import java.util.Optional;

public interface TravelSingUpDAO
{

    /**
     *
     * @param customer
     * @param placeToGo
     * @return
     */
    public boolean singCustomerInForToday(Customer customer, int placeToGo);

    /**
     *
     * @return
     */
    public Optional<TravellingCustomers> getCustomersSignedUpForToday();
}
