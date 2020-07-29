package com.tss.awesomehotel.service.customer;

import com.tss.awesomehotel.dao.customer.CustomerTokenDAO;
import com.tss.awesomehotel.exception.HotelInternalException;
import com.tss.awesomehotel.exception.HotelMasqueradeException;
import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.customer.CustomerToken;
import com.tss.awesomehotel.service.customer.helper.CustomerTokenHelper;
import com.tss.awesomehotel.service.customer.validator.CustomerServiceValidator;
import com.tss.awesomehotel.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class holds the business logic of the tokens. All the specific operations for generating,
 * and generating. Also, here there will be the operations
 * for data validation to make sure all the information that is passed to the underlying layers is in a proper shape.
 */
@Service
public class TokenService
{
    /**
     * The reference tot the persistance layer of the customers
     */
    @Autowired
    @Qualifier("RedisCustomerToken")
    private CustomerTokenDAO customerTokenDAO;

    /**
     * A reference to the customer service
     */
    @Autowired
    private CustomerService customerService;

    /**
     * The length of the token
     */
    @Value("${app.tokens.token_length}")
    private int tokenLength;

    /**
     * The separator used to divide the customer from the token
     */
    @Value("${app.tokens.token_separator}")
    private String tokenSeparator;

    /**
     * The object used to validate the tokens in the string form
     */
    @Autowired
    private CustomerTokenHelper tokenValidator;

    /**
     * A reference to the customer validator
     */
    private final CustomerServiceValidator customerValidator= new CustomerServiceValidator();


    // ============== PUBLIC INTERFACE =============

    /**
     * This method is to check if the token is valid or not
     *
     * @param token the token to check
     * @return <code>true</code> if the token is valid <code>false</code> otherwise
     * @throws HotelMasqueradeException Thrown when an internal error raises
     */
    public boolean checkTokenValidity(@NonNull String token) throws HotelMasqueradeException
    {
        try
        {
            CustomerToken customerToken = this.parseToken(token);
            return this.customerTokenDAO.getTokenForCustomer(customerToken.getCustomerID()).isPresent();
        }catch(HotelInternalException exception)
        {
            throw new HotelMasqueradeException();
        }
    }

    /**
     * Method to return a customer based on its token
     * @param token The token of the customer
     * @return The customer associated with the token
     * @throws HotelMasqueradeException Thrown when an internal error raises
     */
    public Customer getCustomerByToken(String token) throws HotelMasqueradeException
    {
        Customer customer = null;
        if (this.checkTokenValidity(token))
        {
            try
            {
                customer = this.buildCustomerBasedOnToken(token);
            }catch(HotelInternalException exception)
            {
                throw new HotelMasqueradeException();
            }
        }
        return customer;
    }

    // ============== PROTECTED INTERFACE =============

    /**
     * This method is ued to generate the token for the given customer
     * @param customer The customer to check
     * @return A string representing the token for the customer
     *
     * @throws HotelInternalException Thrown when the
     */
    protected String generateAndSaveTokenForCustomer(@NonNull Customer customer) throws HotelInternalException
    {
        String ret;

        Optional<String> tokenContainer = this.checkCustomerTokenByCustomerID(customer.getCustomerID());
        if (tokenContainer.isEmpty())
        {
            ret = this.buildCustomerToken(customer).getToken();
            this.customerTokenDAO.saveCustomerToken(customer.getCustomerID(), ret);
        } else
        {
            ret = tokenContainer.get();
        }
        return ret;
    }

    // ============== PRIVATE METHODS  =============

    /**
     * This method is used to build a customer based on its token
     *
     * @param token The token of the customer
     * @return An instnace of {@link Customer} null otherwise
     * @throws HotelInternalException Thrown when the token is invalid
     */
    private Customer buildCustomerBasedOnToken(String token) throws HotelInternalException
    {
        CustomerToken customerToken = this.parseToken(token);
        return this.customerService.findCustomerByID(customerToken.getCustomerID());
    }

    /**
     * This method check if there is a customer token.
     *
     * @param customerID The customer ID who owns the token
     * @return An {@link Optional} container with the information.
     */
    private Optional<String> checkCustomerTokenByCustomerID(@NonNull String customerID)  throws HotelInternalException
    {
        Optional<String> customerTokenContainer;
        if (StringHelper.checkIfStringContainsSomething(customerID))
        {
            Optional<CustomerToken> tokenContainer = this.customerTokenDAO.getTokenForCustomer(customerID);
            customerTokenContainer = tokenContainer.map(CustomerToken::getToken).or(Optional::empty);
        } else
        {
            throw new HotelInternalException("The customer ID can't be null in order to retrieve the token");
        }
        return customerTokenContainer;
    }

    /**
     * This method is in charge of building a customer token based on the information of
     * said customer
     *
     * @param customer The customer to generate the token for
     * @return An instance of customer token
     */
    private CustomerToken buildCustomerToken(@NonNull Customer customer)
    {
        this.customerValidator.validate(customer);
        this.customerValidator.validateID(customer);

        String fullTokenString = StringHelper.getRandomAlphaNumericString(this.tokenLength)
                    .concat(this.tokenSeparator)
                    .concat(customer.getCustomerID());

        return new CustomerToken(customer.getCustomerID(), fullTokenString);

    }

    /**
     * Method used to parse the token and convert it to a {@link CustomerToken}
     *
     * @param token The token to parse
     * @return A instance of {@link CustomerToken} or null.
     */
    private CustomerToken parseToken(@NonNull String token) throws HotelInternalException
    {
        try
        {
            return this.tokenValidator.convertToken(token);
        }catch(IllegalArgumentException exception)
        {
            throw new HotelInternalException("The provided token is invalid", exception);
        }
    }

}
