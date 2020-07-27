package com.tss.awesomehotel.service;

import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.service.customer.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.data.mongodb.host=localhost", "spring.data.mongodb.port=27017", "spring.data.mongodb.database=awesomehotelTest"})
@AutoConfigureMockMvc
class CustomerServiceTest
{
    // Creating default customer
    private Customer customer;

    @BeforeEach
    public void beforeEachTest()
    {
        customer = new Customer("Test", "test", "test", "password",  "test");
    }

    @AfterEach
    public void afterEachTest()
    {
        this.service.deleteCustomerByID(this.customer.getCustomerID());
    }

    @Autowired
    private CustomerService service;

    @Test
    public void registerCustomer()
    {

        Assertions.assertTrue(this.service.registerCustomer(customer));
        Assertions.assertFalse(this.service.registerCustomer(customer));

        this.testCriticalDataAsNull(customer);
        this.testCriticalDataAsEmpty(customer);

        this.service.deleteCustomerByID(customer.getCustomerID());
    }

    @Test
    public void TestDeleteCustomer()
    {
        Assertions.assertTrue(this.service.deleteCustomerByID(this.customer.getCustomerID()));
        Assertions.assertTrue(this.service.deleteCustomerByID(this.customer.getCustomerID()));
        Assertions.assertTrue(this.service.deleteCustomerByID(this.customer.getCustomerID()));
        Assertions.assertTrue(this.service.deleteCustomerByID(this.customer.getCustomerID()));
        String test = null;
        this.testIllegalArgumentException(() -> this.service.deleteCustomerByID(test));

    }

    private void testThrowInCustomerRegistration(Customer customer)
    {
        this.testIllegalArgumentException(() ->
        {
            this.service.registerCustomer(customer);
        });
    }

    private void testCriticalDataAsNull(Customer customer)
    {
        String firstName = customer.getFirstName();
        customer.setFirstName(null);
        this.testThrowInCustomerRegistration(customer);
        customer.setFirstName(firstName);

        String middleName = customer.getMiddleName();
        customer.setLastName(null);
        this.testThrowInCustomerRegistration(customer);
        customer.setMiddleName(middleName);


        String customerID = customer.getCustomerID();

        customer.setCustomerID(null);
        this.testThrowInCustomerRegistration(customer);

        String customerPassword = customer.getPassword();
        customer.setPassword(null);
        this.testThrowInCustomerRegistration(customer);
        customer.setPassword(customerPassword);

        customer.setCustomerID(customerID);
    }

    private void testCriticalDataAsEmpty(Customer customer)
    {
        String firstName = customer.getFirstName();
        customer.setFirstName("");
        this.testThrowInCustomerRegistration(customer);
        customer.setFirstName(firstName);

        String middleName = customer.getMiddleName();
        customer.setLastName("");
        this.testThrowInCustomerRegistration(customer);
        customer.setMiddleName(middleName);


        String customerID = customer.getCustomerID();

        customer.setCustomerID("");
        this.testThrowInCustomerRegistration(customer);

        String customerPassword = customer.getPassword();
        customer.setPassword("");
        this.testThrowInCustomerRegistration(customer);
        customer.setPassword(customerPassword);

        customer.setCustomerID(customerID);
    }

    private void testIllegalArgumentException(Executable function)
    {
        Assertions.assertThrows(IllegalArgumentException.class, function);

    }


    @Test
    void logCustomerIn()
    {
        this.service.registerCustomer(this.customer);
        this.beforeEachTest();
        String token = this.service.logCustomerIn(this.customer);
        Assertions.assertNotNull(token, "The login could not found a existent customer");
        Assertions.assertEquals(token, this.service.logCustomerIn(this.customer), "The tokens for the same login do not match!");

        this.customer.setPassword("invalid");
        Assertions.assertTrue(this.service.logCustomerIn(this.customer).isBlank(), "The login found a non existent customer");

    }
}