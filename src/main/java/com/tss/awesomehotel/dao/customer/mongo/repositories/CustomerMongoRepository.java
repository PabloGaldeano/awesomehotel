package com.tss.awesomehotel.dao.customer.mongo.repositories;

import com.tss.awesomehotel.model.customer.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * This interface exists only with the purpose to be able to use the connection to
 * mongoDB with the appropriate type, which in this case is {@link Customer}.
 */
public interface CustomerMongoRepository extends MongoRepository<Customer, String>
{

    @Query("{ 'firstName' : ?0}")
    List<Customer> findCustomersByFirstName(@NonNull String firstName);

    @Query("{'_id': ?0}")
    Customer findCustomerByID(@NonNull String customerID);

    @Query("{_id: ?0, password: ?1}")
    Customer findCustomerByIDAndPassword(@NonNull String customerID, @NonNull String customerPassword);
}
