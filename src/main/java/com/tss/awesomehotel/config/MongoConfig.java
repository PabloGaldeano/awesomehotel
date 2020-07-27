package com.tss.awesomehotel.config;

import com.tss.awesomehotel.utils.StringHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig
{

    @Bean
    public MongoDatabaseFactory mongoDbFactory()
    {
        String mongoHost = StringHelper.checkIfStringContainsSomething(System.getenv("MONGO_HOST")) ? System.getenv("MONGO_HOST") : "localhost";
        String mongoPort = StringHelper.checkIfStringContainsSomething(System.getenv("MONGO_PORT")) ? System.getenv("MONGO_PORT") : "27017";
        String mongoDB = StringHelper.checkIfStringContainsSomething(System.getenv("MONGO_DATABASE")) ? System.getenv("MONGO_DATABASE") : "awesomehotel";

        return new SimpleMongoClientDatabaseFactory(String.format("mongodb://%s:%s/%s", mongoHost,mongoPort, mongoDB));
    }


}
