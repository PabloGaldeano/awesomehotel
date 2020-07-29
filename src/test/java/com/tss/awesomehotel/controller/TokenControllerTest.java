package com.tss.awesomehotel.controller;

import com.tss.awesomehotel.controller.generic.GenericCustomerControllerTest;
import com.tss.awesomehotel.model.ServiceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class TokenControllerTest extends GenericCustomerControllerTest
{
    protected MockHttpServletRequestBuilder customerTokenCheckRequestBuilder;


    @Test
    public void identifyCustomer() throws Exception
    {
        this.testCustomer.setCustomerID("Test2");
        this.testCheckInCustomerAndExpectTotalSuccess();
        ServiceResponse response = this.testLogInCustomerAndExpectTotalSuccess();

        // Checcking
        String token = response.getResponseData().toString();
        Assertions.assertEquals(token.split("__")[1], this.testCustomer.getCustomerID(), "The customer ID does not match");

        this.customerTokenCheckRequestBuilder = MockMvcRequestBuilders.get("/token/identify/" + token);
        response = this.executePetitionAndExpectTotalSuccess(this.customerTokenCheckRequestBuilder);
        System.out.println(response.getResponseData());
        this.testCheckOutCustomerAndExpectTotalSuccess();

        this.customerTokenCheckRequestBuilder = MockMvcRequestBuilders.get("/token/identify/wrongToken");
        this.executePetitionAndExpectWrongResponse(this.customerTokenCheckRequestBuilder);

    }

}