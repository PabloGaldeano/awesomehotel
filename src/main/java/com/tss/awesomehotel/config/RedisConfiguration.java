package com.tss.awesomehotel.config;

import com.tss.awesomehotel.utils.EnvironmentHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration
{
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory()
    {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        config.setHostName(EnvironmentHelper.getVariableOrDefault("REDIS_HOST","localhost"));

        JedisConnectionFactory jedisFactory = new JedisConnectionFactory(config);

        return jedisFactory;
    }

    @Bean
    public RedisTemplate<String,Object> redisConnectionFactory()
    {
        RedisTemplate<String,Object> template = new RedisTemplate<>();

        template.setConnectionFactory(this.getJedisConnectionFactory());
        return template;
    }
}
