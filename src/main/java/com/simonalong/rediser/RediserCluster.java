package com.simonalong.rediser;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author shizi
 * @since 2020/3/15 上午12:51
 */
public class RediserCluster extends JedisCluster {

    private List<HostAndPort> hostAndPorts = new ArrayList<>();
    private JedisCluster jedisCluster;
    private JedisPoolConfig poolConfig;
    private String clusterPassword;
    private int connectionTimeout = 1000;
    private int soTimeout = 1000;
    private int maxAttempts = 1;

    public RediserCluster(HostAndPort node) {
        super(node);
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public void bind(String host) {
        if (null == host || "".equals(host)) {
            return;
        }
        String[] ipPortPair = host.split(":");
        hostAndPorts.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
    }

    public void bind(String host, int port) {
        hostAndPorts.add(new HostAndPort(host, port));
    }

    public void start() {
        if (hostAndPorts.size() > 0) {
            if (null != poolConfig) {
                if (null != clusterPassword) {
                    jedisCluster = new JedisCluster(new HashSet<>(hostAndPorts), connectionTimeout, soTimeout, maxAttempts, clusterPassword, poolConfig);
                } else {
                    jedisCluster = new JedisCluster(new HashSet<>(hostAndPorts), connectionTimeout, soTimeout, maxAttempts, poolConfig);
                }
            } else {
                jedisCluster = new JedisCluster(new HashSet<>(hostAndPorts), connectionTimeout, maxAttempts);
            }
        }
    }
}
