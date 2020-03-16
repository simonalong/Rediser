package com.simonalong.rediser;

import com.simonalong.rediser.jedis.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import redis.clients.jedis.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
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
    @Setter
    private JedisPoolConfig poolConfig;
    @Setter
    private String password;
    private volatile Jedis proxyJedis;
    private final Object lock = new Object();
    private Config config = new Config();
    private RedissonClient redissonClient;

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

    public synchronized void start() {
        if (started) {
            return;
        }
        if (null == hostAndPort) {
            throw new RuntimeException("please first bind host and port");
        }
        jedisPool = new JedisPool(poolConfig, hostAndPort.getHost(), hostAndPort.getPort());
        config.useSingleServer().setAddress("redis://" + hostAndPort.getHost() + ":" + hostAndPort.getPort()).setPassword(password).setDatabase(0);
        redissonClient = Redisson.create(config);
        started = true;
    }

    public void dxLock(String key, int waitTime, int leaseTime, Runnable successCall) {
        dxLock(key, waitTime, leaseTime, TimeUnit.MILLISECONDS, successCall, () -> {});
    }

    public Object dxLock(String key, int waitTime, int leaseTime, Callable successCall) {
        return dxLock(key, waitTime, leaseTime, TimeUnit.MILLISECONDS, successCall, () -> null);
    }

    public void dxLock(String key, int waitTime, int leaseTime, TimeUnit timeUnit, Runnable successCall) {
        dxLock(key, waitTime, leaseTime, TimeUnit.MILLISECONDS, successCall, () -> {});
    }

    public Object dxLock(String key, int waitTime, int leaseTime, TimeUnit timeUnit, Callable successCall) {
        return dxLock(key, waitTime, leaseTime, TimeUnit.MILLISECONDS, successCall, () -> null);
    }

    public void dxLock(String key, int waitTime, int leaseTime, Runnable successCall, Runnable failCall) {
        dxLock(key, waitTime, leaseTime, TimeUnit.MILLISECONDS, successCall, failCall);
    }

    public Object dxLock(String key, int waitTime, int leaseTime, Callable successCall, Callable failCall) {
        return dxLock(key, waitTime, leaseTime, TimeUnit.MILLISECONDS, successCall, failCall);
    }

    public void dxLock(String key, int waitTime, int leaseTime, TimeUnit timeUnit, Runnable successCall, Runnable failCall) {
        dxLock(key, waitTime, leaseTime, timeUnit, () -> {
            successCall.run();
            return null;
        }, () -> {
            failCall.run();
            return null;
        });
    }

    /**
     * 分布式加锁
     *
     * @param key         key
     * @param waitTime    加锁等待时间
     * @param leaseTime   加锁的持续时间
     * @param timeUnit    时间单位
     * @param successCall 加锁成功回调处理
     * @param failCall    加锁失败回调处理
     * @return 执行的返回值
     */
    public Object dxLock(String key, int waitTime, int leaseTime, TimeUnit timeUnit, Callable successCall, Callable failCall) {
        if (!started) {
            throw new RuntimeException("please first run start() method");
        }
        RLock disLock = redissonClient.getLock(key);
        try {
            if (disLock.tryLock(waitTime, leaseTime, timeUnit)) {
                return successCall.call();
            } else {
                failCall.call();
            }
        } catch (Exception e) {
            throw new RuntimeException("exception", e);
        } finally {
            if (disLock.isHeldByCurrentThread()) {
                disLock.unlock();
            }
        }
        return null;
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
