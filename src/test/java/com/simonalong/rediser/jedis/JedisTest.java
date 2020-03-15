package com.simonalong.rediser.jedis;

import com.simonalong.rediser.BaseTest;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Assert;
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
    public void testSimple1() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("nihao", "testSimple");
        show(jedis.get("nihao"));
        jedis.close();
    }

    /**
     * 测试不存在的返回值
     */
    @Test
    public void testSimple2() {
        Jedis jedis = new Jedis("localhost", 6379);
        // 返回OK
        Assert.assertEquals(jedis.set("key", "testSimple2"), "OK");
        // 返回OK
        Assert.assertEquals(jedis.set("key", "testSimple2"), "OK");
        jedis.del("key");

        // 返回OK
        Assert.assertEquals(jedis.set("key_nx", "testSimple2", "nx"), "OK");
        // 返回null
        Assert.assertNull(jedis.set("key_nx", "testSimple2", "nx"));
        jedis.del("key_nx");

        // 返回null
        Assert.assertNull(jedis.set("key_xx", "testSimple2", "xx"));
        Assert.assertEquals(jedis.set("key_xx", "testSimple2"), "OK");
        // 返回OK
        Assert.assertEquals(jedis.set("key_xx", "testSimple2", "xx"), "OK");
        jedis.del("key_xx");
        jedis.close();
    }

    /**
     * 测试不存在的返回值
     */
    @Test
    public void testSimple3() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.del("key_nx3");
        // 返回1
        Assert.assertEquals(jedis.setnx("key_nx3", "value"), new Long(1));
        // 返回0
        Assert.assertEquals(jedis.setnx("key_nx3", "value"), new Long(0));
        jedis.del("key_nx3");
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

    /**
     * 测试列表List
     */
    @Test
    public void testList(){
        Jedis jedis = new Jedis("localhost", 6379);
    }
}
