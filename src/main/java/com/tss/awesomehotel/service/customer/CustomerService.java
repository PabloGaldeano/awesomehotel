package com.tss.awesomehotel.service.customer;

import com.tss.awesomehotel.dao.customer.CustomerDAO;
import com.tss.awesomehotel.exception.HotelInternalException;
import com.tss.awesomehotel.exception.HotelMasqueradeException;
import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.service.customer.validator.CustomerServiceValidator;
import com.tss.awesomehotel.utils.StringHelper;
import com.tss.awesomehotel.utils.security.CypherHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class holds the business logic of the customers. All the specific operations for the check-in,
 * log-in and reserve a trip will be written in here. Also, here there will be the operations
 * for data validation to make sure all the information that is passed to the underlying layers is in a proper shape.
 */
@Service
public class CustomerService
{
    /**
     * The reference to the data access object in order to communicate with the database.
     */
    @Autowired
    @Qualifier("CustomerMongo")
    private CustomerDAO customerDAO;

    /**
     * A reference to the token service
     */
     @Autowired
     private TokenService tokenService;

    /**
     * A reference to the validator for the customer
     */
    private final CustomerServiceValidator customerValidator = new CustomerServiceValidator();

    // ============== PUBLIC INTERFACE =============

    /**
     * This method is used to register a customer against the database, it will check that the ID of the customer is null, and that the required fields are not empty or null
     *
     * @param customer The customer to check.
     * @return Whether or not the customer was inserted into the database
     */
    public boolean registerCustomer(Customer customer)
    {
        this.customerValidator.validate(customer);
        this.customerValidator.validateForInserting(customer);
        customer.setPassword(this.cypherCustomerPassword(customer));
        return this.customerDAO.insertCustomerData(customer);
    }

    /**
     * Method used to remove a customer from the database
     *
     * @param customerID The customer ID to remove
     * @return <code>true</code> if the customer was removed successfully, <code>false</code> otherwise
     */
    public boolean deleteCustomerByID(@NonNull String customerID)
    {
        boolean ret;
        if (StringHelper.checkIfStringContainsSomething(customerID))
        {
            ret = this.customerDAO.removeCustomerByID(customerID);
        } else
        {
            throw new IllegalArgumentException("The customer ID can not be empty");
        }
        return ret;
    }

    /**
     * This is the method used to check if the customer is registered in the database and
     * the data matches.
     *
     * @param customer The customer to check
     * @return The token generated for this customer
     */
    public String logCustomerIn(@NonNull Customer customer) throws HotelMasqueradeException
    {
        String ret = "";
        this.customerValidator.validate(customer);
        this.customerValidator.validateCustomerIDAndPassword(customer);
        Optional<Customer> reference = this.customerDAO.retrieveCustomerByIDAndPassword(customer.getCustomerID(),
                this.cypherCustomerPassword(customer));
        if (reference.isPresent())
        {
           ret = this.invokeCustomerTokenGeneration(customer);
        } else
        {
            Logger.getGlobal().log(Level.WARNING, "The customer with ID {0} could not be found in the DB",
                    new Object[]{customer.getCustomerID()});
        }

        return ret;
    }


    // ============== PROTECTED INTERFACE =============

    /**
     * Method to return a customer by its ID from the database
     * @param customerID The customer ID to check
     * @return An isntance of the customer, null otherwise
     */
    protected Customer findCustomerByID(@NonNull String customerID)
    {
        return this.customerDAO.retrieveCustomerData(customerID).orElse(null);
    }

    // ============== PRIVATE METHODS  =============

    /**
     * This method is a wrapper to handle the exception thrown by the token service
     * @param customer The customer to generate the token for
     * @return The token on its string format
     * @throws HotelMasqueradeException Thrown when an internal exception is caught
     */
    private String invokeCustomerTokenGeneration(Customer customer) throws HotelMasqueradeException
    {
        try
        {
            return this.tokenService.generateAndSaveTokenForCustomer(customer);
        } catch (HotelInternalException exception)
        {
            throw new HotelMasqueradeException();
        }
    }

    /**
     * This method is used to cypher the customer password
     * @param customer The customer to cypher its password
     * @return A string representing the customer cyphered password
     */
    private String cypherCustomerPassword(Customer customer)
    {
        return  CypherHelper.digestStringWithMD5AndSalt(customer.getPassword());
    }

}