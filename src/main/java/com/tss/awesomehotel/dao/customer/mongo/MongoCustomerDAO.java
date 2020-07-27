package com.tss.awesomehotel.dao.customer.mongo;

import com.tss.awesomehotel.dao.customer.CustomerDAO;
import com.tss.awesomehotel.dao.customer.mongo.repositories.CustomerMongoRepository;
import com.tss.awesomehotel.model.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the DAO implementation for MongoDB, it uses the default instance
 * of {@link CustomerMongoRepository} in order to operate with the collection of {@link Customer}
 */
@Repository("CustomerMongo")
public class MongoCustomerDAO implements CustomerDAO
{
    @Autowired
    private CustomerMongoRepository mongoConnection;

    /**
     * This method will attempt to return the customer data associated with the given ID. it will return NULL if the customer is not found.
     *
     * @param customerID The ID of the customer to look for in the database
     * @return An instance of the customer, NULL otherwise
     */
    @Override
    public Optional<Customer> retrieveCustomerData(@NonNull String customerID)
    {
        return Optional.ofNullable(this.mongoConnection.findCustomerByID(customerID));
    }

    /**
     * This is the method used to find a customer by its ID and password
     *
     * @param customerID       The customer ID to look for
     * @param customerPassword The customer password
     * @return An {@link Optional} container with the information retrieved from the database
     */
    @Override
    public Optional<Customer> retrieveCustomerByIDAndPassword(@NonNull String customerID, @NonNull String customerPassword)
    {
        return  Optional.ofNullable(this.mongoConnection.findCustomerByIDAndPassword(customerID, customerPassword));
    }

    /**
     * This method is used to write the customer data into the database. This method assumes that the data has ben already verified by the calling layer.
     *
     * @param customer The data of the customer to insert into the database.
     */
    public boolean insertCustomerData(@NonNull Customer customer)
    {
        boolean wasCustomerInserted = false;
        try
        {
            this.mongoConnection.insert(customer);
            wasCustomerInserted = true;
        } catch (Exception ex)
        {
            Logger.getGlobal().log(Level.WARNING,"The customer {0} could not be inserted due to an error: {1}",
                    new Object[]{customer.getCustomerID(), ex.getMessage()});
        }
        return wasCustomerInserted;
    }

    /**
     * This method is used to remove a customer from the database
     * @param customerID The customer ID to delete, can't be null
     * @return <code>true</code> if the customer was deleted, <code>false</code> otherwise
     */
    public boolean removeCustomerByID(@NonNull String customerID)
    {
        boolean ret = true;
        try
        {
            this.mongoConnection.deleteById(customerID);
        }catch (Exception ex)
        {
            Logger.getGlobal().log(Level.SEVERE,"The customer with id {0} could not be registered...",
                    new Object[]{customerID});
            ret = false;
        }
        return ret;
    }

}
