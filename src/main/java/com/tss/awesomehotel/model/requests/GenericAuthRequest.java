package com.tss.awesomehotel.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * This is the generic request for the petitions that requires
 * the user to be logged in. It only contains the token and
 * methods for the JSON engine to do its job
 */
public class GenericAuthRequest implements Serializable
{
    /**
     * The token in this request
     */
    @JsonProperty("token")
    private String token;

    public GenericAuthRequest()
    {

    }

    public GenericAuthRequest(String token)
    {
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
