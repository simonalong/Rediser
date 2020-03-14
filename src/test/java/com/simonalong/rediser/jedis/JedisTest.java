package com.simonalong.rediser.jedis;

import com.simonalong.rediser.BaseTest;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author shizi
 * @since 2020/3/14 下午12:00
 */
public class JedisTest extends BaseTest {

    /**
     * 基本的使用
     */
    @Test
    public void testSimple() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("nihao", "testSimple");
        show(jedis.get("nihao"));
        jedis.close();
    }

    /**
     * 池化使用
     */
    @Test
    public void testPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setLifo(true);
        JedisPool pool = new JedisPool(poolConfig, "localhost", 6379);
        Jedis jedis = pool.getResource();
        jedis.set("nihao", "testPool");
        show(jedis.get("nihao"));
        jedis.close();
        pool.close();
    }

    /**
     * 基本的使用
     */
    @Test
    public void testExpire() {
        String[] serverArray = "redis集群地址".split(",");
        Set<HostAndPort> nodes = new HashSet<>();

        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }

        JedisCluster jedisCluster = new JedisCluster(nodes, 1000, 1000, 1, "redis集群密码", new GenericObjectPoolConfig());
        jedisCluster.set("nihao", "testSimple");
        show(jedisCluster.get("nihao"));
        try {
            jedisCluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
