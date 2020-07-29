package com.tss.awesomehotel.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * This is the request data the customer have to send to be able
 * to sign up for a ride.
 */
public class SignUpForTravelAuthRequest extends GenericAuthRequest
{
    /**
     * This is the id of {@link com.tss.awesomehotel.model.travel.TourStop} where
     * the customer wants to go
     */
    @JsonProperty("to")
    private int stopID;


    public SignUpForTravelAuthRequest(String token, int stopID)
    {
        super(token);
        this.stopID = stopID;
    }

    public int getStopID()
    {
        return stopID;
    }

    public void setStopID(int stopID)
    {
        this.stopID = stopID;
    }
}
