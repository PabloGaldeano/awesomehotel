package com.tss.awesomehotel.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GenericRequest implements Serializable
{
    @JsonProperty("token")
    private String token;

    public GenericRequest()
    {

    }

    public GenericRequest(String token)
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
