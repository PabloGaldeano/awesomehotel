package com.tss.awesomehotel.service.tour;

import com.tss.awesomehotel.model.travel.TourStep;
import com.tss.awesomehotel.model.travel.TourStop;
import com.tss.awesomehotel.model.travel.TravellingCustomers;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class holds the business logic of the Tours. All the specific operations for getting the
 * different stops of the tour. Also, here there will be the operations
 * for data validation to make sure all the information that is passed to the underlying layers is in a proper shape.
 */
@Service
public class TourService
{
    /**
     * This is the reference to the tour sign-up service.
     */
    @Autowired
    private TourSignUpService tourSignUpService;

    /**
     * The object representing the shortest path
     */
    private static TourStep shortestTour;

    // ============== PUBLIC STATIC INTERFACE =============

    /**
     * Method used to set the shortest tour representation, given by the
     * bootstrap operation {@link com.tss.awesomehotel.config.bootstrap.operations.CalculateShortestTourOperation}
     * @param tour The instance of {@link TourStep }
     */
    public static void setShortestTour(Optional<TourStep> tour)
    {
        shortestTour = tour.orElse(new TourStep(null,0.0,null));
    }

    // ============== PUBLIC INTERFACE =============

    /**
     * This method will query the {@link TourSignUpService} in order to get all the
     * customers that have signed up for a certain day to go to the different
     * {@link TourStop}
     *
     * @return An instance of {@link TourStep} containing the customers signed up
     * for each stop
     */
    public TourStep getShortTourWithCustomers()
    {
        TourStep nextStep = shortestTour;
        TravellingCustomers customersSignedUp = this.tourSignUpService.getTravellingCustomersForToday();
        while(nextStep != null)
        {
            nextStep.setSignedUpCustomers(customersSignedUp.getCustomerForCertainStop(nextStep.getPathInformation().getTravelPathID()));
            nextStep = nextStep.getNextStep();
        }
        return shortestTour;
    }


}
