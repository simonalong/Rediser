package com.simonalong.rediser;

import lombok.Data;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author shizi
 * @since 2020/5/20 ${time} 11:37 PM
 */
@Data
public class RediserClusterConfig {

    /**
     * 链接超时时间
     */
    private int connectionTimeout = 1000;
    private int soTimeout = 1000;
    /**
     * 最大尝试次数
     */
    private int maxAttempts = 1;
    /**
     * 密码
     */
    private String password;
    private JedisPoolConfig poolConfig;

    private ShardedJedisPool shardedJedisPool;
}
