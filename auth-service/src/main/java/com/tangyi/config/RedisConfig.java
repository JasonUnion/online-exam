package com.tangyi.config;

import com.tangyi.service.CacheService;
import com.tangyi.utils.RedisObjectSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置
 * Created by tangyi on 2017/4/5.
 */
@Configuration
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public CacheService generateRedisService(){
        CacheService cacheService = new CacheService();
        cacheService.setConnectionFactory(jedisConnectionFactory());
        return cacheService;
    }
}