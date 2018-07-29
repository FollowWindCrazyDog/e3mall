package com.followwindd.e3mall.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SingleJedisClient implements JedisClient {

    private JedisPool jedisPool;
    @Override
    public String set(String key, String value) {
        Jedis resource = jedisPool.getResource();
        String set = resource.set(key, value);
        resource.close();
        return set;
    }

    @Override
    public String get(String key) {
        Jedis resource = jedisPool.getResource();
        String value = resource.get(key);
        resource.close();
        return value;
    }

    @Override
    public Boolean exists(String key) {
        Jedis resource = jedisPool.getResource();
        Boolean exists = resource.exists(key);
        resource.close();
        return exists;
    }

    public Long expire(String key, int seconds) {
        Jedis resource = jedisPool.getResource();
        Long expire = resource.expire(key, seconds);
        resource.close();
        return expire;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public Long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.incr(key);
        jedis.close();
        return result;
    }

    @Override
    public Long hset(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hset(key, field, value);
        jedis.close();
        return result;
    }

    @Override
    public String hget(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.hget(key, field);
        jedis.close();
        return result;
    }

    @Override
    public Long hdel(String key, String... field) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hdel(key, field);
        jedis.close();
        return result;
    }
}
