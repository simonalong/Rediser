package com.simonalong.rediser.jedis;


import redis.clients.jedis.Jedis;

/**
 * @author shizi
 * @since 2020/3/15 上午12:02
 */
public interface JedisGetter {

    /**
     * 获取Jedis实例
     *
     * @return 代理的Jedis实例
     */
    Jedis getJedis();
}
