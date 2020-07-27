package com.tss.awesomehotel.config;

import com.tss.awesomehotel.dao.travel.TravelDAO;
import com.tss.awesomehotel.model.travel.TravelPath;
import com.tss.awesomehotel.service.travel.TravelService;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class Bootstrap implements CommandLineRunner
{
    @Autowired
    @Qualifier("MongoTravelPathsDAO")
    private TravelDAO pathsDao;

    @Override
    public void run(String... args) throws Exception
    {
        // Reading the paths from the DB
        List<TravelPath> travelPaths = this.pathsDao.getAllPaths();
        SimpleWeightedGraph<TravelPath, DefaultWeightedEdge> routeGraph =
                new SimpleWeightedGraph<>(DefaultWeightedEdge.class);


        travelPaths.stream().map(travelPath ->
        {
            routeGraph.addVertex(travelPath);
            System.out.println("Adding the stop " + travelPath.getName());
            return travelPath;
        }).collect(Collectors.toList()).forEach(travelPath ->
        {
            travelPath.getConnections().stream().forEach(connectionsMap ->
            {
                Optional<TravelPath> possiblePath = travelPaths.stream().filter(path -> path.getTravelPathID() == connectionsMap.get("to")).findFirst();
                if (possiblePath.isPresent())
                {
                    TravelPath destination = possiblePath.get();
                    System.out.println("Adding the route path from " + travelPath.getName() + " to " + destination.getName());
                    routeGraph.setEdgeWeight(routeGraph.addEdge(travelPath, destination), connectionsMap.get("distance"));
                }
                else
                {
                    Logger.getGlobal().log(Level.WARNING, "Can not find the destination with ID: {0} in the list retrieved from the DB",
                            new Object[]{connectionsMap.get("to")});
                }
            });
        });

        System.out.println("Calculating shortest path");

        HeldKarpTSP<TravelPath,DefaultWeightedEdge> path = new HeldKarpTSP<>();

        GraphPath<TravelPath, DefaultWeightedEdge> tour = path.getTour(routeGraph);
        TravelService.setShortestTour(tour);

    }
}
