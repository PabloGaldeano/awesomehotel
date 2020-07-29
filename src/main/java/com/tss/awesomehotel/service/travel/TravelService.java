package com.tss.awesomehotel.service.travel;

import com.tss.awesomehotel.model.travel.TourStep;
import com.tss.awesomehotel.model.travel.TourStop;
import com.tss.awesomehotel.model.travel.TravellingCustomers;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelService
{
    @Autowired
    private TravelSignUpService travelSignUpService;



    private static TourStep shortestTour;

    public static void setShortestTour(GraphPath<TourStop, DefaultWeightedEdge> tour)
    {
        if (tour != null && tour.getVertexList().size() > 0)
        {
            Graph<TourStop, DefaultWeightedEdge> shortestTourGraph = tour.getGraph();
            List<TourStop> vertexList = tour.getVertexList();
            shortestTour = new TourStep(null, 0.0f, vertexList.get(0));
            TourStep nextStepPointer = shortestTour;
            for(int i = 1; i<vertexList.size(); i++)
            {
                TourStop source = vertexList.get(i-1);
                TourStop destination = vertexList.get(i);
                DefaultWeightedEdge edge = shortestTourGraph.getEdge(source, destination);
                double weigth = shortestTourGraph.getEdgeWeight(edge);

                nextStepPointer.setNextStep(new TourStep(null, weigth + nextStepPointer.getTotalDistance(), destination));
                nextStepPointer = nextStepPointer.getNextStep();
            }
        }
        else
        {
            throw new IllegalArgumentException("The tour can't be null");
        }
    }

    public TourStep getShortTourWithCustomers()
    {
        TourStep nextStep = shortestTour;
        TravellingCustomers customersSignedUp = this.travelSignUpService.getTravellingCustomersForToday();
        while(nextStep != null)
        {
            nextStep.setSignedUpCustomers(customersSignedUp.getCustomerForCertainStop(nextStep.getPathInformation().getTravelPathID()));
            nextStep = nextStep.getNextStep();
        }
        return shortestTour;
    }


}
