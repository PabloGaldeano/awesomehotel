package com.tss.awesomehotel.service.customer;

import com.tss.awesomehotel.dao.customer.CustomerDAO;
import com.tss.awesomehotel.model.customer.Customer;
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

     @Autowired
     private TokenService tokenService;

    /**
     * This method is used to register a customer against the database, it will check that the ID of the customer is null, and that the required fields are not empty or null
     *
     * @param customer The customer to check.
     * @return Whether or not the customer was inserted into the database
     */
    public boolean registerCustomer(Customer customer)
    {
        this.validateCustomerInfoForInserting(customer);
        customer.setPassword(CypherHelper.cypherString(customer.getPassword()));
        return this.customerDAO.insertCustomerData(customer);
    }

    /**
     * This method will make sure all the required information from the customer does exist
     * and it has some value to be inserted. This will avoid customer without a first and a
     * last name.
     *
     * @param customer The customer data used to check the names.
     */
    private void validateCustomerNames(@NonNull Customer customer)
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
    public String logCustomerIn(@NonNull Customer customer)
    {
        String ret = "";
        if (customer != null)
        {
            this.validateCustomerIDAndPassword(customer);
            String customerPasswordEncrypted = CypherHelper.cypherString(customer.getPassword());
            Optional<Customer> reference = this.customerDAO.retrieveCustomerByIDAndPassword(customer.getCustomerID(), customerPasswordEncrypted);
            if (reference.isPresent())
            {
               ret = this.tokenService.generateAndSaveTokenForCustomer(customer);
            } else
            {
                Logger.getGlobal().log(Level.WARNING, "The customer with ID {0} could not be found in the DB",
                        new Object[]{customer.getCustomerID()});
            }
        } else
        {
            throw new IllegalArgumentException("The customer can not be null!");
        }
        return ret;
    }

    public Customer findCustomerByID(@NonNull String customerID)
    {
        return this.customerDAO.retrieveCustomerData(customerID).orElse(null);
    }

    /**
     * This methods checks all the data of the customer needed for insertion.
     *
     * @param customer The customer to check
     */
    private void validateCustomerInfoForInserting(Customer customer)
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
    private void validateCustomerIDAndPassword(Customer customer)
    {
        if (!StringHelper.checkIfStringContainsSomething(customer.getCustomerID()))
        {
            throw new IllegalArgumentException("The ID of the customer has to be valid");
        }

        if (!StringHelper.checkIfStringContainsSomething(customer.getPassword()))
        {
            throw new IllegalArgumentException("The password of the customer has to be valid");
        }
    }

}