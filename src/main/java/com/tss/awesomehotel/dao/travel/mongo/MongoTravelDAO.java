package com.tss.awesomehotel.dao.travel.mongo;

import com.tss.awesomehotel.dao.travel.TravelDAO;
import com.tss.awesomehotel.dao.travel.mongo.repositories.TravelPathsMongoRepository;
import com.tss.awesomehotel.model.travel.TourStop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is the DAO implementation for MongoDB, it uses the default instance
 * of {@link TravelPathsMongoRepository} in order to operate with the collection of {@link TourStop}
 */
@Repository("MongoTravelPathsDAO")
public class MongoTravelDAO implements TravelDAO
{

    /**
     * The connection to mongo
     */
    @Autowired
    private TravelPathsMongoRepository mongoConnection;

    /**
     * Method to get all the paths available
     * @return A list of paths
     */
    @Override
    public List<TourStop> getAllPaths()
    {
        return this.mongoConnection.findAll();
    }


}
