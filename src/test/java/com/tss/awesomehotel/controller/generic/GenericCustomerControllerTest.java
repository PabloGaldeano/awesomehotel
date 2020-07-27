package com.tss.awesomehotel.controller.generic;

import com.tss.awesomehotel.model.customer.Customer;
import com.tss.awesomehotel.model.ServiceResponse;
import com.tss.awesomehotel.utils.Parser;
import org.junit.After;
import org.junit.Before;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class GenericCustomerControllerTest extends GenericControllerTest
{
    protected MockHttpServletRequestBuilder customerCheckInRequestBuilder;
    protected MockHttpServletRequestBuilder customerCheckOutRequestBuilder;
    protected MockHttpServletRequestBuilder customerLogInRequestBuilder;
    protected Customer testCustomer;
    @Before
    public void beforeTest()
    {
        this.reInitializeVariables();
    }

    @After
    public void afterTest() throws Exception
    {
        this.regenerateCustomer();
        this.testCheckOutCustomerAndExpectTotalSuccess();
    }

    protected void reInitializeVariables()
    {
        this.regenerateCustomer();
        this.customerCheckInRequestBuilder = MockMvcRequestBuilders.post("/customer/check_in");
        this.customerCheckOutRequestBuilder = MockMvcRequestBuilders.delete("/customer/check_out");
        this.customerLogInRequestBuilder = MockMvcRequestBuilders.post("/customer/log_in");

        this.writeHeadersInRequest(this.customerCheckInRequestBuilder);
        this.writeHeadersInRequest(this.customerCheckOutRequestBuilder);
        this.writeHeadersInRequest(this.customerLogInRequestBuilder);
    }

    protected ServiceResponse testCheckOutCustomerAndExpectTotalSuccess() throws Exception
    {
        return this.executePetitionAndExpectTotalSuccess(customerCheckOutRequestBuilder.content(this.testCustomer.getCustomerID()));
    }

    protected ServiceResponse testLogInCustomerAndExpectTotalSuccess() throws Exception
    {
        this.writeCustomerInRequest(this.customerLogInRequestBuilder);
        return this.executePetitionAndExpectTotalSuccess(this.customerLogInRequestBuilder);
    }

    protected ServiceResponse testLoginAndExpectFailure() throws Exception
    {
        this.writeCustomerInRequest(this.customerLogInRequestBuilder);
        return this.executePetitionAndExpectWrongResponse(this.customerLogInRequestBuilder);
    }

    protected ServiceResponse testCheckInCustomerAndExpectTotalSuccess() throws Exception
    {
        this.writeCustomerInRequest(this.customerCheckInRequestBuilder);
        return this.executePetitionAndExpectTotalSuccess(this.customerCheckInRequestBuilder);
    }

    protected ServiceResponse testCheckInAndExpectFailure() throws Exception
    {
        this.writeCustomerInRequest(this.customerCheckInRequestBuilder);
        return this.executePetitionAndExpectWrongResponse(this.customerCheckInRequestBuilder);
    }
    protected void regenerateCustomer()
    {
        this.testCustomer = new Customer("TestController", "Test", null, "password", "Test");
    }
    protected void writeCustomerInRequest(MockHttpServletRequestBuilder destinationBuilder) throws Exception
    {
        destinationBuilder.content(Parser.mapper.writeValueAsString(this.testCustomer));
    }


}
