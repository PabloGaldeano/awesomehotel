package com.tss.awesomehotel.service.travel;

import com.tss.awesomehotel.dao.travel.TravelSingUpDAO;
import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.travel.TravellingCustomers;
import com.tss.awesomehotel.utils.dates.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class TravelSignUpService
{
    @Autowired
    private TravelSingUpDAO travelSingUpDAO;

    public boolean signCustomerUp(Customer customer, int stop)
    {
        return this.travelSingUpDAO.singCustomerInForToday(customer, stop);
    }

    public TravellingCustomers getTravellingCustomersForToday()
    {
        Optional<TravellingCustomers> customersSignedUpToday = this.travelSingUpDAO.getCustomersSignedUpForToday();
        return customersSignedUpToday.orElse(new TravellingCustomers(DateHelper.getCurrentDayOfYear(), new HashMap<>()));
    }
}
