package com.tangyi.service;

import com.tangyi.utils.SerializeUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tangyi on 2017/4/5.
 */
public class CacheService extends RedisTemplate<Serializable,Serializable> {

    /**
     * 设值进缓存，存入redis前先将keys和value序列化
     * @param keys
     * @param value
     */
    public void set(final String keys, final Object value) {

        this.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(CacheService.this.getStringSerializer().serialize(keys), SerializeUtils.serialize(value));
                return null;
            }
        });

    }


    /**
     * 从缓存重获取内容
     * @param key
     * @return
     */
    public Object get(final String key) {

        return this.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {

                byte[] keySer = CacheService.this.getStringSerializer().serialize(key);

                if (connection.exists(keySer)) {
                    byte[] value = connection.get(keySer);
                    return SerializeUtils.unserialize(value);
                } else {
                    return null;
                }

            }
        });

    }

    /**
     * 根据key删除value
     * @param key
     */
    public void del(final String key) {
        this.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.del(CacheService.this.getStringSerializer().serialize(key));
                return null;
            }
        });
    };


    public Object expire(final String key, final Object value, final long timeout) {

        Object result = this.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.pSetEx(CacheService.this.getStringSerializer().serialize(key),timeout, SerializeUtils.serialize(value));
                return true;
            }
        });
        return result;

    }


    /**
     * 清理缓存
     */
    public void flushAll() {
        this.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushAll();
                return null;
            }
        });
    }


    /**
     * 获缓存里的所有key
     * @param pattern
     * @return
     */
    public Set<String> keys(final String pattern) {
        return (Set<String>) this.execute(new RedisCallback<Object>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<byte[]> set = connection.keys(CacheService.this.getStringSerializer().serialize(pattern));
                Set<String> rtn = new HashSet<String>();
                for(byte[] b :set) {
                    rtn.add(new String(b));
                }
                return rtn;
            }
        });
    }
}

