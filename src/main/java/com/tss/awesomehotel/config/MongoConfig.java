package com.tss.awesomehotel.config;

import com.tss.awesomehotel.utils.EnvironmentHelper;
import com.tss.awesomehotel.utils.StringHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * This class is used to dynamically configure mongo based on the system
 * variables
 */
@Configuration
public class MongoConfig
{

    @Bean
    public MongoDatabaseFactory mongoDbFactory()
    {
        String mongoHost = EnvironmentHelper.getVariableOrDefault("MONGO_HOST","localhost") ;
        String mongoPort = EnvironmentHelper.getVariableOrDefault("MONGO_PORT","27017");
        String mongoDB = EnvironmentHelper.getVariableOrDefault("MONGO_DATABASE","awesomehotel");

        return new SimpleMongoClientDatabaseFactory(String.format("mongodb://%s:%s/%s", mongoHost,mongoPort, mongoDB));
    }


}
