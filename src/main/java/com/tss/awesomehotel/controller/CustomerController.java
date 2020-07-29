package com.tss.awesomehotel.controller;

import com.tss.awesomehotel.controller.basic.GenericController;
import com.tss.awesomehotel.exception.MasqueradeException;
import com.tss.awesomehotel.exception.codes.ErrorCodes;
import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.ServiceResponse;
import com.tss.awesomehotel.service.customer.CustomerService;
import com.tss.awesomehotel.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

/**
 * This is the controller that provides all the functionality related to the customer, such as log-in, check-in, and reserve a trip.
 */

@RequestMapping("/customer")
@RestController
public class CustomerController extends GenericController
{
    @Autowired
    public CustomerService customerService;

    /**
     * This method is used to check a customer in inside the database if and only if the customer does not exists.
     *
     * @param customer This is the customer to attempt to check in
     */

    @PostMapping("/check_in")
    public ServiceResponse checkCustomerIn(@RequestBody Customer customer)
    {
        ServiceResponse toReturn;
        try
        {
            boolean wasTheCustomerCheckedIn = this.customerService.registerCustomer(customer);
            toReturn = (wasTheCustomerCheckedIn) ? ServiceResponse.createSuccessResponse(customer.getFirstName()) :
                    ServiceResponse.fromErrorCode(ErrorCodes.REGISTRATION);
        } catch (IllegalArgumentException illegalArgumentException)
        {
            toReturn = ServiceResponse.createErrorResponse(illegalArgumentException.getMessage());
        }
        return toReturn;
    }


    /**
     * This is a function used to check out a customer.
     *
     * @param customerID The ID of the customer to checkout
     * @return A {@link ServiceResponse } instance with the result of the operation
     */
    @DeleteMapping("/check_out")
    public ServiceResponse checkOutCustomer(@NonNull @RequestBody String customerID)
    {
        return this.handleCallExceptions(() ->
        {
            boolean wasTheCustomerCheckedIn = this.customerService.deleteCustomerByID(customerID);
            return (wasTheCustomerCheckedIn) ? ServiceResponse.createSuccessResponse(null) :
                    ServiceResponse.fromErrorCode(ErrorCodes.MASQUERADE);
        });
    }

    /**
     * Method used to log a customer in the system
     *
     * @param customer The customer to to check
     * @return a response object with the operation result
     */
    @PostMapping("/log_in")
    public ServiceResponse logCustomerIn(@NonNull @RequestBody Customer customer)
    {
        return this.handleCallExceptions(() ->
        {
            ServiceResponse toReturn;
            try
            {
                String customerToken = this.customerService.logCustomerIn(customer);
                return (StringHelper.checkIfStringContainsSomething(customerToken)) ? ServiceResponse.createSuccessResponse(customerToken) :
                        ServiceResponse.fromErrorCode(ErrorCodes.INVALID_CREDENTIALS);
            } catch (MasqueradeException ex)
            {
                toReturn = ServiceResponse.createErrorResponse(ex.getMessage(), ex.getExceptionCode());
            }
            return toReturn;
        });

    }
}