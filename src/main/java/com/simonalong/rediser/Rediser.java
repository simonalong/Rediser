package com.simonalong.rediser;

import com.alibaba.fastjson.JSON;
import com.simonalong.rediser.jedis.DefaultAdvancedJedisCommands;
import com.simonalong.rediser.jedis.DefaultJedisCommands;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import redis.clients.jedis.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author shizi
 * @since 2020/3/14 上午11:47
 */
public class Rediser implements DefaultAdvancedJedisCommands, DefaultJedisCommands {

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
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, JSON.toJSONString(data));
        jedis.close();
    }

    public <T> T get(String key, Class<T> tClass) {
        // todo
        return null;
    }

    private void execute(Runnable runnable) {
        runnable.run();
    }

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            return null;
        }
    }

    public void setLocal(String key, Object data) {

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

    @Override
    public Jedis getJedis() {
        JedisProxy jedisProxy = new JedisProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Jedis.class);
        enhancer.setCallback(jedisProxy);
        return (Jedis) enhancer.create();
    }

    private class JedisProxy implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) {
            try (Jedis jedis = jedisPool.getResource()) {
                return method.invoke(jedis, objects);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("run method " + method.getName() + " error", e);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        }
    }
}
