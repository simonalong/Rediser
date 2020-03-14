package com.simonalong.rediser;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.TimeUnit;

/**
 * @author shizi
 * @since 2020/3/14 上午11:47
 */
public class Rediser extends Jedis {

    private static final Rediser INSTANCE = new Rediser();

    private Rediser() {}

    public static Rediser getInstance() {
        return INSTANCE;
    }

    public void bind(String host) {
        // todo
    }

    public void bind(String ip, int host) {
        // todo
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        // todo
    }

    public void Lock(String key, int outtime, TimeUnit timeUnit) {
        // todo
    }

    public void unlock(String key) {
        // todo
    }

    public void set(String key, Object data) {
        // todo
    }

    public void setLocal(String key, Object data) {

    }

    @Override
    public String get(String key){
        // todo
        return null;
    }

    public <T> T get(String key, Class<T> tClass) {
        // todo
        return null;
    }

    public String getLocal(String key) {
        // todo
        return null;
    }

    public <T> T getLocal(String key, Class<T> tClass) {
        // todo
        return null;
    }
}
