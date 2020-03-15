package com.simonalong.rediser;

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
public class Rediser implements RediserObjectSetter, RediserObjectGetter, RediserHashHandler, DefaultJedis {

    private static final Rediser INSTANCE = new Rediser();
    private volatile Boolean started = false;
    private HostAndPort hostAndPort;
    private JedisPool jedisPool = new JedisPool();
    private JedisPoolConfig poolConfig;
    private volatile Jedis proxyJedis;
    private final Object lock = new Object();

    private Rediser() {
        init();
    }

    public static Rediser getInstance() {
        return INSTANCE;
    }

    private void init() {
        poolConfig = new JedisPoolConfig();
    }

    public void bind(String host) {
        if (null == host || "".equals(host)) {
            return;
        }
        String[] ipPortPair = host.split(":");
        hostAndPort = new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim()));
    }

    public void bind(String host, int port) {
        hostAndPort = new HostAndPort(host, port);
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public synchronized void start() {
        if (started) {
            return;
        }
        if (null == hostAndPort) {
            throw new RuntimeException("please first bind host and port");
        }
        jedisPool = new JedisPool(poolConfig, hostAndPort.getHost(), hostAndPort.getPort());
        started = true;
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
        if (null != proxyJedis) {
            return proxyJedis;
        }

        synchronized (lock) {
            if (null != proxyJedis) {
                return proxyJedis;
            }
            JedisProxy jedisProxy = JedisProxy.getInstance();
            jedisProxy.setJedisPool(jedisPool);
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Jedis.class);
            enhancer.setCallback(jedisProxy);
            proxyJedis = (Jedis) enhancer.create();
            return proxyJedis;
        }
    }

    private static class JedisProxy implements MethodInterceptor {

        private static JedisProxy INSTANCE = new JedisProxy();
        private JedisPool jedisPool;

        private JedisProxy() {}

        static JedisProxy getInstance() {
            return INSTANCE;
        }

        void setJedisPool(JedisPool jedisPool) {
            this.jedisPool = jedisPool;
        }

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
