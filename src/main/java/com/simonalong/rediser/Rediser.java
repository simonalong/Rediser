package com.simonalong.rediser;

import com.alibaba.fastjson.JSON;
import com.simonalong.rediser.jedis.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import redis.clients.jedis.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author shizi
 * @since 2020/3/14 上午11:47
 */
@Slf4j
public class Rediser implements DefaultBasicCommands, DefaultJedisCommands, DefaultMultiKeyCommands, DefaultAdvancedJedisCommands, DefaultScriptingCommands, DefaultClusterCommands, DefaultSentinelCommands {

    private static final Rediser INSTANCE = new Rediser();
    private volatile Boolean started = false;
    private HostAndPort hostAndPort;
    private JedisPool jedisPool = new JedisPool();
    private JedisPoolConfig poolConfig;

    private Rediser() {
        init();
    }

    public static Rediser getInstance() {
        return INSTANCE;
    }

    private void init() {
        poolConfig = new JedisPoolConfig();
    }

    public void connect(String host) {
        if (null == host || "".equals(host)) {
            return;
        }
        String[] ipPortPair = host.split(":");
        hostAndPort = new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim()));
    }

    public void connect(String host, int port) {
        hostAndPort = new HostAndPort(host, port);
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public synchronized void start() {
        if (started) {
            return;
        }
        jedisPool = new JedisPool(poolConfig, hostAndPort.getHost(), hostAndPort.getPort());
        started = true;
    }

    public void set(String key, Object data) {
        getJedis().set(key, JSON.toJSONString(data));
    }

    public <T> T get(String key, Class<T> tClass) {
        return JSON.parseObject(getJedis().get(key), tClass);
    }

    public void setLocal(String key, Object data) {
        // todo
    }

    public String getLocal(String key) {
        // todo
        return null;
    }

    public <T> T getLocal(String key, Class<T> tClass) {
        // todo
        return null;
    }

    public void lock(String key, int outTime, TimeUnit timeUnit) {
        // todo
    }

    public void unlock(String key) {
        // todo
    }

    /**
     * 获取动态代理的Jedis
     *
     * @return 代理后的Jedis
     */
    @Override
    public Jedis getJedis() {
        if (!started) {
            throw new RuntimeException("please first run start() method");
        }
        JedisProxy jedisProxy = new JedisProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Jedis.class);
        enhancer.setCallback(jedisProxy);
        return (Jedis) enhancer.create();
    }

    private class JedisProxy implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws InvocationTargetException, IllegalAccessException {
            try (Jedis jedis = jedisPool.getResource()) {
                return method.invoke(jedis, objects);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                log.error("run method " + method.getName() + " error", e);
                throw e;
            }
        }
    }
}
