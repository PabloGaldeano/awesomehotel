package com.tss.awesomehotel.model.travel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TourStep
{

    @JsonProperty("totalDistance")
    private double totalDistance;

    @JsonProperty("Path")
    private TourStop pathInformation;

    @JsonProperty("nextStep")
    private TourStep nextStep;

    @JsonProperty("customers")
    private List<String> signedUpCustomers;


    public TourStep(TourStep nextStep, double total_distance, TourStop pathInformation)
    {
        this.nextStep = nextStep;
        this.totalDistance = total_distance;
        this.pathInformation = pathInformation;
    }

    public TourStep getNextStep()
    {
        return nextStep;
    }

    public void setNextStep(TourStep nextStep)
    {
        this.nextStep = nextStep;
    }

    public double getTotalDistance()
    {
        return totalDistance;
    }

    public void setTotalDistance(double total_distance)
    {
        this.totalDistance = total_distance;
    }

    public TourStop getPathInformation()
    {
        return pathInformation;
    }

    public void setPathInformation(TourStop pathInformation)
    {
        this.pathInformation = pathInformation;
    }

    public List<String> getSignedUpCustomers()
    {
        return signedUpCustomers;
    }

    public void setSignedUpCustomers(List<String> signedUpCustomers)
    {
        this.signedUpCustomers = signedUpCustomers;
    }
}
