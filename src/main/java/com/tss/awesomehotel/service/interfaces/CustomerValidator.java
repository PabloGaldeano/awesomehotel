package com.tss.awesomehotel.service.interfaces;

import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.interfaces.ModelInsertionValidator;
import org.springframework.lang.NonNull;

public interface CustomerValidator extends ModelInsertionValidator<Customer>
{
    /**
     * This method makes sure the customer object has a valid ID and Password, otherwise
     * it will throw a {@link IllegalArgumentException}
     *
     * @param customer The customer to check
     */
    void validateCustomerIDAndPassword(Customer customer);


    /**
     * This method will make sure all the required information from the customer does exist
     * and it has some value to be inserted. This will avoid customer without a first and a
     * last name.
     *
     * @param customer The customer data used to check the names.
     */
    void validateCustomerNames(@NonNull Customer customer);

}
