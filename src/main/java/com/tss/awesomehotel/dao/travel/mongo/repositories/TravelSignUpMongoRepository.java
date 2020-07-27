package com.tss.awesomehotel.dao.travel.mongo.repositories;

import com.tss.awesomehotel.model.travel.TravellingCustomers;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TravelSignUpMongoRepository extends MongoRepository<TravellingCustomers, Integer>
{

}
