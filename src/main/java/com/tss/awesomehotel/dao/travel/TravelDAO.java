package com.tss.awesomehotel.dao.travel;

import com.tss.awesomehotel.model.travel.TravelPath;

import java.util.List;

public interface TravelDAO
{

    /**
     *
     * @return
     */
    public List<TravelPath> getAllPaths();

}
