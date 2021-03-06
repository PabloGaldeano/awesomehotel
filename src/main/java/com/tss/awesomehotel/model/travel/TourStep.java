package com.tss.awesomehotel.model.travel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * This class models the different steps in the tour
 * offered by the hotel which is the shortest path
 * within all the points
 */
public class TourStep
{

    /**
     * The total distance travelled up to this step
     */
    @JsonProperty("totalDistance")
    private double totalDistance;

    /**
     * The information of the step, which is one of the
     * available stops
     */
    @JsonProperty("Path")
    private TourStop pathInformation;

    /**
     * The next step in the tour
     */
    @JsonProperty("nextStep")
    private TourStep nextStep;

    /**
     * A list of customers full names that are signed up to go
     * to this step
     */
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
