package com.tss.awesomehotel.controller;

import com.tss.awesomehotel.controller.generic.GenericCustomerControllerTest;
import com.tss.awesomehotel.model.ServiceResponse;
import com.tss.awesomehotel.model.requests.GenericAuthRequest;
import com.tss.awesomehotel.model.requests.SignUpForTravelAuthRequest;
import com.tss.awesomehotel.model.travel.TravellingCustomers;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class TravelControllerTest extends GenericCustomerControllerTest
{

    @Autowired
    private CrudRepository<TravellingCustomers, Integer> repository;

    private MockHttpServletRequestBuilder getShortestPathRequestBuilder = MockMvcRequestBuilders.post("/tour/itinerary/");
    private MockHttpServletRequestBuilder signUpForARideRequestBuilder = MockMvcRequestBuilders.post("/tour/sign_up/");

    @Test
    public void getShortestPath() throws Exception
    {

        // Logging in
        this.testCheckInCustomerAndExpectTotalSuccess();
        ServiceResponse loginResponse = this.testLogInCustomerAndExpectTotalSuccess();

        GenericAuthRequest requestData = new GenericAuthRequest();
        requestData.setToken(loginResponse.getResponseData().toString());

        this.testGetShortestPathAndExpectTotalSuccess(requestData);

    }

    @Test
    public void singUpForARide() throws Exception
    {

        this.testCheckInCustomerAndExpectTotalSuccess();
        ServiceResponse loginResponse = this.testLogInCustomerAndExpectTotalSuccess();

        String customerToken = loginResponse.getResponseData().toString();


        this.testSignUpForARideAndExpectSuccess(customerToken);
        this.testCustomerIsSignedInOnTourStep(1, customerToken);

        // Keeping DB clean
        this.repository.deleteAll();

        // Testing wrong data
        this.testSignUpWithBadToken();
    }

    private void testSignUpWithBadToken() throws Exception
    {
        this.prepareSignUpPetition("invalid");
        this.executePetitionAndExpectWrongResponse(this.signUpForARideRequestBuilder);
    }

    private void testSignUpForARideAndExpectSuccess(String customerToken) throws Exception
    {
        this.prepareSignUpPetition(customerToken);
        this.executePetitionAndExpectTotalSuccess(this.signUpForARideRequestBuilder);
    }

    private void prepareSignUpPetition(String customerToken) throws Exception
    {
        SignUpForTravelAuthRequest travelRequestData = new SignUpForTravelAuthRequest(customerToken, 1);

        this.writeHeadersInRequest(this.signUpForARideRequestBuilder);
        this.writeObjectInRequestContent(signUpForARideRequestBuilder, travelRequestData);
    }

    private void testCustomerIsSignedInOnTourStep(int tourStep, String token) throws Exception
    {
        GenericAuthRequest requestData = new GenericAuthRequest();
        requestData.setToken(token);

        Map<String,Object> tripInfo = this.testGetShortestPathAndExpectTotalSuccess(requestData);

        while(tripInfo != null)
        {
            Map<String,Object> pathInfo = (Map<String,Object>) tripInfo.get("pathInformation");
            double pathID = (Double) pathInfo.get("id");
            if (Double.compare(tourStep, pathID) == 0)
            {
                List<String> tripCustomers = (List<String>) tripInfo.get("customers");
                Assertions.assertTrue(tripCustomers.get(0).contains(this.testCustomer.getFullName()));
                break;
            }
            tripInfo = (Map<String,Object>) tripInfo.get("nextStep");
        }
    }

    private Map<String,Object> testGetShortestPathAndExpectTotalSuccess(GenericAuthRequest requestData) throws Exception
    {
        this.writeHeadersInRequest(this.getShortestPathRequestBuilder);
        this.writeObjectInRequestContent(getShortestPathRequestBuilder, requestData);

        ServiceResponse response = this.executePetitionAndExpectTotalSuccess(this.getShortestPathRequestBuilder);
        return (Map<String,Object>)response.getResponseData();
    }


}