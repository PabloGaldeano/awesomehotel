package com.tss.awesomehotel.service.customer.validator;

import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.service.interfaces.CustomerValidator;
import com.tss.awesomehotel.utils.StringHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;


public class CustomerServiceValidator implements CustomerValidator
{

    /**
     * This method is used to validate the customer object
     *
     * @param customer The customer to validate
     */
    public void validate(Customer customer)
    {
        if (customer == null)
        {
            throw new IllegalArgumentException("Customer can't be null");
        }
    }

    /**
     * This method will make sure all the required information from the customer does exist
     * and it has some value to be inserted. This will avoid customer without a first and a
     * last name.
     *
     * @param customer The customer data used to check the names.
     */
    public void validateCustomerNames(@NonNull Customer customer)
    {
        if (!StringHelper.checkIfStringContainsSomething(customer.getFirstName()))
        {
            throw new IllegalArgumentException("The first name of the customer has to be valid");
        }
        if (!StringHelper.checkIfStringContainsSomething(customer.getLastName()))
        {
            throw new IllegalArgumentException("The last name of the customer has to be valid");
        }
    }

    /**
     * This methods checks all the data of the customer needed for insertion.
     *
     * @param customer The customer to check
     */
    public void validateForInserting(Customer customer)
    {
        this.validateCustomerNames(customer);
        this.validateCustomerIDAndPassword(customer);
    }

    /**
     * This method makes sure the customer object has a valid ID and Password, otherwise
     * it will throw a {@link IllegalArgumentException}
     *
     * @param customer The customer to check
     */
    public void validateCustomerIDAndPassword(Customer customer)
    {
        this.validateID(customer);

        if (!StringHelper.checkIfStringContainsSomething(customer.getPassword()))
        {
            throw new IllegalArgumentException("The password of the customer has to be valid");
        }
    }

    /**
     * Method to validate the id of the customer object
     * @param customer The customer object to check
     */
    public void validateID(@NonNull Customer customer)
    {
        if (!StringHelper.checkIfStringContainsSomething(customer.getCustomerID()))
        {
            throw new IllegalArgumentException("The ID of the customer has to be valid");
        }
    }

}
