package com.tss.awesomehotel.controller;

import com.tss.awesomehotel.controller.generic.GenericCustomerControllerTest;
import com.tss.awesomehotel.model.ServiceResponse;
import com.tss.awesomehotel.utils.Parser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.data.mongodb.host=localhost", "spring.data.mongodb.port=27017", "spring.data.mongodb.database=awesomehotelTest"})
@AutoConfigureMockMvc
public class CustomerControllerTest extends GenericCustomerControllerTest
{


    @Test
    public void checkCustomerIn() throws Exception
    {
        this.testCheckInCustomerAndExpectTotalSuccess();
        this.testCustomer.setCustomerID(null);
        this.testCheckInAndExpectFailure();

        this.regenerateCustomer();
        this.testCustomer.setFirstName(null);
        this.testCheckInAndExpectFailure();

    }

    @Test
    public void testLogInCustomer() throws Exception
    {
        this.testCheckInCustomerAndExpectTotalSuccess();
        ServiceResponse response = this.testLogInCustomerAndExpectTotalSuccess();

        // Login with wrong data
        this.testCustomer.setCustomerID("irrelevant");
        this.testLoginAndExpectFailure();
    }

    @Test
    public void checkCustomerCheckInWithWrongData() throws Exception
    {
        testCustomer.setFirstName(null);
        this.writeCustomerInRequest(this.customerCheckInRequestBuilder);
        this.executePetitionAndExpectWrongResponse(customerCheckInRequestBuilder);
    }

}