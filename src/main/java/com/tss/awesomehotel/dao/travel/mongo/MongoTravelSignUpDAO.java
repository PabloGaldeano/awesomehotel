package com.tss.awesomehotel.dao.travel.mongo;

import com.tss.awesomehotel.dao.travel.TravelSingUpDAO;
import com.tss.awesomehotel.dao.travel.mongo.repositories.TravelSignUpMongoRepository;
import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.travel.TravellingCustomers;
import com.tss.awesomehotel.utils.StringHelper;
import com.tss.awesomehotel.utils.dates.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Optional;

@Repository("MongoTravelSignUpDAO")
public class MongoTravelSignUpDAO implements TravelSingUpDAO
{
    @Autowired
    private TravelSignUpMongoRepository mongoRepository;

    @Override
    public boolean singCustomerInForToday(Customer customer, int placeToGo)
    {
        boolean signedUp = false;
        if (customer != null && StringHelper.checkIfStringContainsSomething(customer.getFullName()))
        {
            Optional<TravellingCustomers> signUpsOfToday = this.getCustomersSignedUpForToday();
            TravellingCustomers customers = signUpsOfToday.orElseGet(() -> new TravellingCustomers(DateHelper.getCurrentDayOfYear(), new HashMap<>()));
            customers.signCustomerFor(placeToGo, customer.getFullName());
            this.mongoRepository.save(customers);
            signedUp = true;
        }
        return signedUp;
    }

    @Override
    public Optional<TravellingCustomers> getCustomersSignedUpForToday()
    {
        return this.mongoRepository.findById(DateHelper.getCurrentDayOfYear());
    }
}
