package com.simonalong.rediser.base;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import org.junit.Test;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author shizi
 * @since 2020/3/14 下午12:49
 */
public class RediserPoolTest extends BaseTest {

    /**
     * 该类会提供默认的连接池配置，如果要自己配置，则可以设置
     */
    @Test
    public void testSetPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(1000);

        Rediser rediser = Rediser.getInstance();
        rediser.setPoolConfig(poolConfig);

        rediser.set("rediser", "testCreate");
        show(rediser.get("rediser"));
    }
}
