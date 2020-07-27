package com.tss.awesomehotel.dto;

import java.io.Serializable;
import java.util.List;

public class TourStepDTO implements Serializable
{
    /**
     * The Serializable serialVersionUID
     */
    private static final long serialVersionUID = 1L;



    private double totalDistance;

    private TravelPathDTO pathInformation;

    private TourStepDTO nextStep;

    private List<String> customers;

    public double getTotalDistance()
    {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance)
    {
        this.totalDistance = totalDistance;
    }

    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

    public TravelPathDTO getPathInformation()
    {
        return pathInformation;
    }

    public void setPathInformation(TravelPathDTO pathInformation)
    {
        this.pathInformation = pathInformation;
    }

    public TourStepDTO getNextStep()
    {
        return nextStep;
    }

    public void setNextStep(TourStepDTO nextStep)
    {
        this.nextStep = nextStep;
    }

    public List<String> getCustomers()
    {
        return customers;
    }

    public void setCustomers(List<String> customers)
    {
        this.customers = customers;
    }
}
