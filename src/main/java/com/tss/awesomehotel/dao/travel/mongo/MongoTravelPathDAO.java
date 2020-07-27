package com.tss.awesomehotel.dao.travel.mongo;

import com.tss.awesomehotel.dao.travel.TravelDAO;
import com.tss.awesomehotel.dao.travel.mongo.repositories.TravelPathsMongoRepository;
import com.tss.awesomehotel.model.travel.TravelPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MongoTravelPathsDAO")
public class MongoTravelPathDAO implements TravelDAO
{

    @Autowired
    private TravelPathsMongoRepository mongoConnection;

    @Override
    public List<TravelPath> getAllPaths()
    {
        return this.mongoConnection.findAll();
    }


}
