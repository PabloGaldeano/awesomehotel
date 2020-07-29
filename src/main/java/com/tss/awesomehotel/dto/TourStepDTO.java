package com.tss.awesomehotel.dto;

import java.io.Serializable;
import java.util.List;

/**
 * This is the DTO of the {@link com.tss.awesomehotel.model.travel.TourStep}
 */
public class TourStepDTO implements Serializable
{
    /**
     * The Serializable serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Distance travelled up to this point
     */
    private double totalDistance;

    /**
     * The path information
     */
    private TourStopDTO pathInformation;

    /**
     * The next step in the path
     */
    private TourStepDTO nextStep;

    /**
     * The customers that were heading to this stop
     */
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

    public TourStopDTO getPathInformation()
    {
        return pathInformation;
    }

    public void setPathInformation(TourStopDTO pathInformation)
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
