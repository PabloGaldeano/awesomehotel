package com.tss.awesomehotel.service.tour;

import com.tss.awesomehotel.dao.travel.TravelSingUpDAO;
import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.travel.TravellingCustomers;
import com.tss.awesomehotel.service.customer.validator.CustomerServiceValidator;
import com.tss.awesomehotel.utils.dates.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

/**
 * This class holds the business logic of the customers. All the specific operations for
 * reserving a trip will be written in here. Also, here there will be the operations
 * for data validation to make sure all the information that is passed to the underlying layers is in a proper shape.
 */
@Service
public class TourSignUpService
{
    /**
     * The reference to the persistance layer
     */
    @Autowired
    private TravelSingUpDAO travelSingUpDAO;

    /**
     * The object used to validate
     */
    private final CustomerServiceValidator customerValidator = new CustomerServiceValidator();

    // ============== PUBLIC INTERFACE =============

    /**
     * This method is used to sign a customer up for a stop in the tour
     *
     * @param customer The customer to sign up
     * @param stop The ID of the stop
     * @return <code>true</code> if the customer was signed up successfully <code>false</code> otherwise
     */
    public boolean signCustomerUp(Customer customer, int stop)
    {
        this.customerValidator.validate(customer);
        this.customerValidator.validateCustomerNames(customer);
        return this.travelSingUpDAO.singCustomerInForToday(customer, stop);
    }

    /**
     * This method will return an instance of {@link TravellingCustomers} representing
     * all of the customer signed up for travelling in the current day.
     *
     * @return The instance of {@link TravellingCustomers}
     */
    public TravellingCustomers getTravellingCustomersForToday()
    {
        Optional<TravellingCustomers> customersSignedUpToday = this.travelSingUpDAO.getCustomersSignedUpForToday();
        return customersSignedUpToday.orElse(new TravellingCustomers(DateHelper.getCurrentDayOfYear(), new HashMap<>()));
    }
}
