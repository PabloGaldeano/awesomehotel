package com.tss.awesomehotel.utils.graph;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.cycle.ChinesePostman;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm;
import org.jgrapht.alg.matching.blossom.v5.KolmogorovWeightedMatching;
import org.jgrapht.alg.matching.blossom.v5.ObjectiveSense;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.alg.shortestpath.JohnsonShortestPaths;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.alg.spanning.PrimMinimumSpanningTree;
import org.jgrapht.alg.tour.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.ClosestFirstIterator;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest
{

    @Test
    void getPath()
    {
        DefaultUndirectedWeightedGraph<Integer, DefaultWeightedEdge> g = new DefaultUndirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);

        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(5);

        g.setEdgeWeight(g.addEdge(0,1),5.0f);
        g.setEdgeWeight(g.addEdge(0,4),6.0f);
        g.setEdgeWeight(g.addEdge(1,2),8.0f);
        g.setEdgeWeight(g.addEdge(1,3),7.0f);
        g.setEdgeWeight(g.addEdge(1,5),6.0f);
        g.setEdgeWeight(g.addEdge(2,3),4.0f);
        g.setEdgeWeight(g.addEdge(2,4),3.0f);
        g.setEdgeWeight(g.addEdge(3,4),2.0f);
        g.setEdgeWeight(g.addEdge(3,5),8.0f);
        g.setEdgeWeight(g.addEdge(4,5),9.0f);

        HeldKarpTSP<Integer,DefaultWeightedEdge> path = new HeldKarpTSP<>();

        GraphPath<Integer, DefaultWeightedEdge> tour = path.getTour(g);

        for(Integer a : tour.getVertexList())
        {
            System.out.println(a);
        }
    }


}