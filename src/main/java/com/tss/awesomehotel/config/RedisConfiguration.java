package com.tss.awesomehotel.config;

import com.tss.awesomehotel.utils.EnvironmentHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Same as {@link MongoConfig} but for redis
 */
@Configuration
public class RedisConfiguration
{
    /**
     * Instantiates the cache connection factory
     * @return the jedis pool config instance.
     */
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory()
    {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        config.setHostName(EnvironmentHelper.getVariableOrDefault("REDIS_HOST","localhost"));

        return new JedisConnectionFactory(config);
    }

    /**
     * Creates the redis template
     * @return An instance of the redis template for spring
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate()
    {
        RedisTemplate<String,Object> template = new RedisTemplate<>();

        template.setConnectionFactory(this.getJedisConnectionFactory());
        return template;
    }
}
