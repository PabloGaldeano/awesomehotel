package com.tss.awesomehotel.dao.travel;

import com.tss.awesomehotel.model.travel.TourStop;

import java.util.List;


public interface TravelDAO
{

    /**
     * Method to get all the paths available
     * @return A list of paths
     */
    public List<TourStop> getAllPaths();

}
