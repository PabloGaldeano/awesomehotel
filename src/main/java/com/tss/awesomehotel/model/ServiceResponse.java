package com.tss.awesomehotel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tss.awesomehotel.exception.codes.ErrorCodes;

import java.security.Provider;

/**
 * This is the uniform response returned by the system, it contains all stuff needed to show the user the most accurate information about the result of the invoked operation.
 */
public class ServiceResponse
{
    /**
     * This attribute represents if the request was a success or not
     */
    @JsonProperty("success")
    private boolean executedSuccessfully;
    /**
     * The description of the error, empty string in case if there were no errors.
     */
    @JsonProperty("errorMessage")
    private String errorMessage;
    /**
     * The data returned by the invoked request.
     */
    @JsonProperty("data")
    private Object responseData;

    @JsonProperty("errorCode")
    private int errorCode;

    /**
     * This method will create a success response with the payload equals to the data received by parameter
     *
     * @param responseData The data of the response
     */
    public static ServiceResponse createSuccessResponse(Object responseData)
    {
        ServiceResponse toReturn = new ServiceResponse();
        toReturn.executedSuccessfully = true;
        toReturn.errorMessage = null;
        toReturn.responseData = responseData;
        return toReturn;
    }

    /**
     * This method will create an error response with the error message received by parameter.
     *
     * @param errorMessage The message of the error that wants to be returned. Can't be NULL
     */
    public static ServiceResponse createErrorResponse(String errorMessage)
    {
        ServiceResponse toReturn = new ServiceResponse();
        toReturn.executedSuccessfully = false;
        if (errorMessage == null || errorMessage.isBlank())
        {
            throw new IllegalArgumentException("The error message can't be null or blank");
        }
        toReturn.errorMessage = errorMessage;
        toReturn.responseData = null;
        toReturn.errorCode = -1;
        return toReturn;
    }

    /**
     * This method instantiates an object based on the information
     * of the literal received by parameter
     *
     * @param source The source literal to ge the information from
     * @return An instance of this class
     */
    public static ServiceResponse fromErrorCode(ErrorCodes source)
    {
        return createErrorResponse(source.getErrorMessage(), source.getErrorCode());
    }

    /**
     * This method will create an error response with the error message and code received by parameter.
     *
     * @param errorMessage The message of the error that wants to be returned. Can't be NULL
     * @param errorCode    The code of the error
     */
    public static ServiceResponse createErrorResponse(String errorMessage, int errorCode)
    {
        ServiceResponse toReturn = createErrorResponse(errorMessage);
        toReturn.errorCode = errorCode;
        return toReturn;
    }


    public boolean isExecutedSuccessfully()
    {
        return executedSuccessfully;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public Object getResponseData()
    {
        return responseData;
    }
}