package com.lrs.dianda.weather.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by lrs on 2018/4/3.
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {
    private volatile JedisConnectionFactory mJedisConnectionFactory;
    private volatile RedisTemplate<String, String> mRedisTemplate;
    private volatile RedisCacheManager mRedisCacheManager;

    public RedisCacheConfig() {
        super();
    }

    public RedisCacheConfig(JedisConnectionFactory mJedisConnectionFactory, RedisTemplate<String, String> mRedisTemplate,
                            RedisCacheManager mRedisCacheManager) {
        super();
        this.mJedisConnectionFactory = mJedisConnectionFactory;
        this.mRedisTemplate = mRedisTemplate;
        this.mRedisCacheManager = mRedisCacheManager;
    }

    public JedisConnectionFactory redisConnectionFactory() {
        return mJedisConnectionFactory;
    }

    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        return mRedisTemplate;
    }

    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
        return mRedisCacheManager;
    }

}
