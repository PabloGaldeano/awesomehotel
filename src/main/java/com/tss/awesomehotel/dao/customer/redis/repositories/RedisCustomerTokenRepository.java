package com.tss.awesomehotel.dao.customer.redis.repositories;

import com.tss.awesomehotel.model.customer.CustomerToken;
import org.springframework.data.repository.CrudRepository;

public interface RedisCustomerTokenRepository extends CrudRepository<CustomerToken, String>
{
}
