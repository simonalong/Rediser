package com.simonalong.rediser;

import lombok.Setter;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author shizi
 * @since 2020/3/15 上午12:51
 */
public class RediserCluster extends JedisCluster {

    private static final RediserCluster INSTANCE = new RediserCluster();
    @Setter
    private List<HostAndPort> hostAndPorts = new ArrayList<>();
    @Setter
    private RediserClusterConfig rediserClusterConfig = new RediserClusterConfig();
    @Setter
    private JedisCluster jedisCluster;


    private RediserCluster() {
        super(new HostAndPort("", 0));
    }

    public static RediserCluster getInstance() {
        return INSTANCE;
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
            if (null != rediserClusterConfig) {
                int connectionTimeout = rediserClusterConfig.getConnectionTimeout();
                int soTimeout = rediserClusterConfig.getSoTimeout();
                int maxAttempts = rediserClusterConfig.getMaxAttempts();
                String password = rediserClusterConfig.getPassword();
                JedisPoolConfig poolConfig = rediserClusterConfig.getPoolConfig();
                if (null != poolConfig) {
                    if (null != password) {
                        jedisCluster = new JedisCluster(new HashSet<>(hostAndPorts), connectionTimeout, soTimeout, maxAttempts, password, poolConfig);
                    } else {
                        jedisCluster = new JedisCluster(new HashSet<>(hostAndPorts), connectionTimeout, soTimeout, maxAttempts, poolConfig);
                    }
                } else {
                    jedisCluster = new JedisCluster(new HashSet<>(hostAndPorts), connectionTimeout, maxAttempts);
                }

            }
        }
    }
}
