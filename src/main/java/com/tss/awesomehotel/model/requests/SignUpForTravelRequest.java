package com.tss.awesomehotel.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SignUpForTravelRequest extends  GenericRequest
{
    @JsonProperty("to")
    private int to;


    public SignUpForTravelRequest(String token, int to)
    {
        super(token);
        this.to = to;
    }

    public int getTo()
    {
        return to;
    }

    public void setTo(int to)
    {
        this.to = to;
    }
}
