package com.tss.awesomehotel.dao.travel.mongo.repositories;

import com.tss.awesomehotel.model.travel.TravelPath;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TravelPathsMongoRepository extends MongoRepository<TravelPath, Double>
{

}
