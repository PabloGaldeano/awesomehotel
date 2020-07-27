package com.tss.awesomehotel.controller.generic;

import com.tss.awesomehotel.model.ServiceResponse;
import com.tss.awesomehotel.utils.Parser;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class GenericControllerTest
{



    @Autowired
    private MockMvc mockMvc;

    public ServiceResponse executePetitionAndExpectTotalSuccess(MockHttpServletRequestBuilder builder) throws Exception
    {
        MvcResult result = this.executeMockPetitionAndExpectOK(builder);
        ServiceResponse response = this.getServiceResponseFromResult(result);
        this.testSuccessServiceResponse(response);
        return response;
    }

    public ServiceResponse executePetitionAndExpectWrongResponse(MockHttpServletRequestBuilder builder) throws Exception
    {
        MvcResult result = this.executeMockPetitionAndExpectOK(builder);
        ServiceResponse response = this.getServiceResponseFromResult(result);
        this.testErrorServiceResponse(response);
        return response;
    }

    protected MvcResult executeMockPetitionAndExpectOK(MockHttpServletRequestBuilder builder) throws Exception
    {
        return this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    }

    protected ServiceResponse getServiceResponseFromResult(MvcResult result) throws Exception
    {
        return Parser.mapper.readValue(result.getResponse().getContentAsString(), ServiceResponse.class);
    }

    public void testSuccessServiceResponse(ServiceResponse response)
    {
        Assert.assertTrue("The success flag is false and it shouldn't!", response.isExecutedSuccessfully());
        Assert.assertEquals("There is an error message and it shouldn't!",null, response.getErrorMessage());
    }

    public void testErrorServiceResponse(ServiceResponse response)
    {

        Assert.assertFalse("The success flag is true and it shouldn't!",response.isExecutedSuccessfully());
        Assert.assertNotNull("There isn't an error message and it should!", response.getErrorMessage());
        Assert.assertNull("There is some data in the response and it shouldn't!", response.getResponseData());
    }

    protected void writeHeadersInRequest(MockHttpServletRequestBuilder destinationBuilder)
    {
        destinationBuilder.header("Content-Type", "application/json");
    }



}
