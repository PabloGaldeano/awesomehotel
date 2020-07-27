package com.tss.awesomehotel.dao.customer;

import com.tss.awesomehotel.model.customer.Customer;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface CustomerDAO
{

    /**
     * This method will attempt to return the customer data associated with the given ID. it will return NULL if the customer is not found.
     *
     * @param customerID A customer model object, null if the customer does not exists.
     * @return An {@link Optional} container with the information retrieved from the database
     */
    public Optional<Customer> retrieveCustomerData(String customerID);

    /**
     * This is the method used to find a customer by its ID and password
     *
     * @param customerID       The customer ID to look for
     * @param customerPassword The customer password
     * @return An {@link Optional} container with the information retrieved from the database
     */
    public Optional<Customer> retrieveCustomerByIDAndPassword(@NonNull String customerID, @NonNull String customerPassword);

    /**
     * This method is used to write the customer data into the database. This method assumes that the data has ben already verified by the calling layer.
     *
     * @param customer The data of the customer to insert into the database.
     * @return Whether or not the customer was inserted into the database
     */
    public boolean insertCustomerData(Customer customer);

    /**
     * This method is used to delete a customer by ID.
     *
     * @param customerID The customer ID to delete, can't be null
     */
    public boolean removeCustomerByID(@NonNull String customerID);


}
