package com.tss.awesomehotel.config.bootstrap.operations;

import com.tss.awesomehotel.dao.travel.TravelDAO;
import com.tss.awesomehotel.model.travel.TourStep;
import com.tss.awesomehotel.model.travel.TourStop;
import com.tss.awesomehotel.service.tour.TourService;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class is in charge of reading the database and get all the
 * different Tour stops from the database and compute the shortest path
 * then it will inject it into the {@link TourService} class for it
 * to be available.
 *
 * This class approaches this problem by implementing the solution
 * of the Travelling Salesman Problem or TSP for short.
 */
@Component
public class CalculateShortestTourOperation implements BootstrapOperation
{
    /**
     * The connection to the database
     */
    @Autowired
    private TravelDAO pathsDao;



    /**
     * The route graph that will be computed with all the database information
     */
    private SimpleWeightedGraph<TourStop, DefaultWeightedEdge> routeGraph;

    /**
     * The list of tour stops in the database
     */
    private List<TourStop> tourStops;

    // ============== PUBLIC INTERFACE =============

    public BootstrapOperation generateOperation()
    {
        return new CalculateShortestTourOperation();
    }


    /**
     * Entry point of this class, it will perform all the operations.
     */
    @Override
    public void executeBootstrapOperation()
    {
        // Reading the paths from the DB
        this.tourStops = this.pathsDao.getAllPaths();
        this.routeGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        tourStops.stream().peek(travelPath ->
        {
            routeGraph.addVertex(travelPath);
            System.out.println("Adding the stop " + travelPath.getName());
        }).collect(Collectors.toList()).forEach(travelPath ->
                travelPath.getConnections().forEach(connectionsMap ->
                        this.parsePathConnections(travelPath, connectionsMap)));

        this.calculateShortestPath();
        System.out.println("Calculating shortest path");
    }

    // ============== PRIVATE METHODS  =============

    /**
     * This method is used to calculate the shortest path that will
     * cover all the vertex in the graph.
     */
    private void calculateShortestPath()
    {
        HeldKarpTSP<TourStop, DefaultWeightedEdge> path = new HeldKarpTSP<>();
        GraphPath<TourStop, DefaultWeightedEdge> tour = path.getTour(routeGraph);
        TourService.setShortestTour(Optional.of(this.convertGraphToTourStep(tour)));
    }

    /**
     * This method will convert the returned tour by the algorithm to an instance
     * of {@link TourStep} that will be used for the {@link TourService}
     *
     * @param tour The graph tour to convert
     * @return An instance of {@link TourStep} with the information of the tour
     */
    private TourStep convertGraphToTourStep(GraphPath<TourStop, DefaultWeightedEdge> tour)
    {
        Graph<TourStop, DefaultWeightedEdge> shortestTourGraph = tour.getGraph();
        ArrayList<TourStop> vertexList = (ArrayList<TourStop>) tour.getVertexList();

        vertexList.remove(vertexList.size() -1);
        TourStep shortestTour = new TourStep(null, 0.0f, vertexList.get(0));
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
        return shortestTour;
    }

    /**
     * This method will create the {@link SimpleWeightedGraph} based on the information
     * that is currently on the database.
     *
     * @param tourStop The stop to process
     * @param connectionsMap The connections of said stop
     */
    private void parsePathConnections(TourStop tourStop, HashMap<String, Double> connectionsMap)
    {
        Optional<TourStop> possiblePath = tourStops.stream().filter(path -> path.getTravelPathID() == connectionsMap.get("to")).findFirst();
        if (possiblePath.isPresent())
        {
            TourStop destination = possiblePath.get();
            System.out.println("Adding the route path from " + tourStop.getName() + " to " + destination.getName());
            routeGraph.setEdgeWeight(routeGraph.addEdge(tourStop, destination), connectionsMap.get("distance"));
        } else
        {
            Logger.getGlobal().log(Level.WARNING, "Can not find the destination with ID: {0} in the list retrieved from the DB",
                    new Object[]{connectionsMap.get("to")});
        }
    }
}
