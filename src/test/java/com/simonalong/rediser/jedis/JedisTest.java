package com.simonalong.rediser.jedis;

import com.simonalong.rediser.BaseTest;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("nihao", "testSimple", );
        show(jedis.get("nihao"));
        jedis.close();
    }
}
