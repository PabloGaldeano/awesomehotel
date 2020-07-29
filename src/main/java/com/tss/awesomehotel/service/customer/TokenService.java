package com.tss.awesomehotel.service.customer;

import com.tss.awesomehotel.dao.customer.CustomerTokenDAO;
import com.tss.awesomehotel.exception.InternalHotelException;
import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.customer.CustomerToken;
import com.tss.awesomehotel.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService
{
    @Autowired
    @Qualifier("RedisCustomerToken")
    private CustomerTokenDAO customerTokenDAO;

    @Autowired
    private CustomerService customerService;

    @Value("${app.tokens.token_length}")
    private int tokenLength;

    @Value("${app.tokens.token_separator}")
    private String tokenSeparator;

    protected String generateAndSaveTokenForCustomer(@NonNull Customer customer) throws InternalHotelException
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

    /**
     * This method check if there is a customer token.
     *
     * @param customerID The customer ID who owns the token
     * @return An {@link Optional} container with the information.
     */
    private Optional<String> checkCustomerTokenByCustomerID(@NonNull String customerID)  throws InternalHotelException
    {
        Optional<String> customerTokenContainer;
        if (StringHelper.checkIfStringContainsSomething(customerID))
        {
            Optional<CustomerToken> tokenContainer = this.customerTokenDAO.getTokenForCustomer(customerID);
            customerTokenContainer = tokenContainer.map(CustomerToken::getToken).or(() -> Optional.empty());
        } else
        {
            throw new InternalHotelException("The customer ID can't be null in order to retrieve the token");
        }
        return customerTokenContainer;
    }


    public boolean checkTokenValidity(@NonNull String token)
    {
        boolean isValid = false;
        try
        {
            CustomerToken customerToken = this.parseToken(token);
            isValid = this.customerTokenDAO.getTokenForCustomer(customerToken.getCustomerID()).isPresent();
        }catch(InternalHotelException exception)
        {
            System.out.println("Error while checking the token -> " + exception.getMessage());
        }
        return isValid;
    }


    private CustomerToken buildCustomerToken(@NonNull Customer customer) throws InternalHotelException
    {
        CustomerToken customerTokenEntity;
        if (customer != null && StringHelper.checkIfStringContainsSomething(customer.getCustomerID()))
        {
            String fullTokenString = StringHelper.getRandomAlphaNumericString(this.tokenLength)
                    .concat(this.tokenSeparator)
                    .concat(customer.getCustomerID());

            customerTokenEntity = new CustomerToken(customer.getCustomerID(), fullTokenString);
        } else
        {
            throw new InternalHotelException("The customer can't be null in order to generate the token");
        }
        return customerTokenEntity;
    }

    private CustomerToken parseToken(@NonNull String token) throws InternalHotelException
    {
        CustomerToken parsedTokenObject;
        if (StringHelper.checkIfStringContainsSomething(token) && token.indexOf(this.tokenSeparator) > 0)
        {
            String[] tokenParts = token.split(this.tokenSeparator);
            parsedTokenObject = new CustomerToken(tokenParts[1], tokenParts[0]);
        } else
        {
            throw new InternalHotelException("The token can't be null and must include the separator");
        }
        return parsedTokenObject;
    }

    public Customer getCustomerByToken(String token) throws InternalHotelException
    {
        Customer customer = null;
        if (this.checkTokenValidity(token))
        {
            CustomerToken customerToken = this.parseToken(token);
            customer = this.customerService.findCustomerByID(customerToken.getCustomerID());
        }
        return customer;
    }

}
