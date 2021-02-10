package com.i2021.springcache.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configurable
public class CacheConfig {


    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        //默认开启缓存空值，可以防止缓存穿透
        defaultCacheConfig = defaultCacheConfig.serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        //序列化value的方式
        defaultCacheConfig = defaultCacheConfig
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofHours(1));

        return defaultCacheConfig;
    }
}
