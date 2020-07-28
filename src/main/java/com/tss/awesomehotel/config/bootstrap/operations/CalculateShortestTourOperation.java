package com.tss.awesomehotel.config.bootstrap.operations;

import com.tss.awesomehotel.dao.travel.TravelDAO;
import com.tss.awesomehotel.model.travel.TravelPath;
import com.tss.awesomehotel.service.travel.TravelService;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class CalculateShortestTourOperation implements BootstrapOperation
{
    @Autowired
    private TravelDAO pathsDao;

    private SimpleWeightedGraph<TravelPath, DefaultWeightedEdge> routeGraph;

    private List<TravelPath> travelPaths;

    @Override
    public void executeBootstrapOperation()
    {
        // Reading the paths from the DB
        this.travelPaths = this.pathsDao.getAllPaths();
        this.routeGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        travelPaths.stream().peek(travelPath ->
        {
            routeGraph.addVertex(travelPath);
            System.out.println("Adding the stop " + travelPath.getName());
        }).collect(Collectors.toList()).forEach(travelPath ->
                travelPath.getConnections().forEach(connectionsMap ->
                        this.parsePathConnections(travelPath, connectionsMap)));

        this.calculateShortestPath();
        System.out.println("Calculating shortest path");
    }

    private void calculateShortestPath()
    {
        HeldKarpTSP<TravelPath, DefaultWeightedEdge> path = new HeldKarpTSP<>();
        GraphPath<TravelPath, DefaultWeightedEdge> tour = path.getTour(routeGraph);
        TravelService.setShortestTour(tour);
    }

    private void parsePathConnections(TravelPath travelPath, HashMap<String, Double> connectionsMap)
    {
        Optional<TravelPath> possiblePath = travelPaths.stream().filter(path -> path.getTravelPathID() == connectionsMap.get("to")).findFirst();
        if (possiblePath.isPresent())
        {
            TravelPath destination = possiblePath.get();
            System.out.println("Adding the route path from " + travelPath.getName() + " to " + destination.getName());
            routeGraph.setEdgeWeight(routeGraph.addEdge(travelPath, destination), connectionsMap.get("distance"));
        } else
        {
            Logger.getGlobal().log(Level.WARNING, "Can not find the destination with ID: {0} in the list retrieved from the DB",
                    new Object[]{connectionsMap.get("to")});
        }
    }
}
