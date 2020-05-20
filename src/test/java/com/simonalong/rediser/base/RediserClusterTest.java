package com.simonalong.rediser.base;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import com.simonalong.rediser.RediserCluster;
import com.simonalong.rediser.RediserClusterConfig;
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
        RediserCluster rediser = RediserCluster.getInstance();
        rediser.bind("localhost:6379");
        rediser.bind("localhost:6380");
        rediser.bind("localhost:6381");
        rediser.bind("localhost:6382");
        rediser.bind("localhost:6383");
        rediser.bind("localhost:6384");

        RediserClusterConfig config = new RediserClusterConfig();
        config.setMaxAttempts(1000);
        rediser.setRediserClusterConfig(config);

        rediser.start();

        rediser.set("rediser", "testCreate");
        show(rediser.get("rediser"));
    }
}
