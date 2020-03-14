package com.simonalong.rediser.base;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import org.junit.Test;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author shizi
 * @since 2020/3/14 下午12:49
 */
public class RediserClusterTest extends BaseTest {

    /**
     * 测试链接Redis集群方式
     */
    @Test
    public void testCluster() {
        Rediser rediser = Rediser.getInstance();
        rediser.connect("localhost:6379");
        rediser.connect("localhost:6380");
        rediser.connect("localhost:6381");
        rediser.connect("localhost:6382");
        rediser.connect("localhost:6383");
        rediser.connect("localhost:6384");

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(1000);
        rediser.setPoolConfig(poolConfig);

        rediser.set("rediser", "testCreate");
        show(rediser.get("rediser"));
    }
}
