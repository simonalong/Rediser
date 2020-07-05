package com.simonalong.rediser.jedis.replication;

import com.simonalong.rediser.BaseTest;
import org.junit.Test;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @author shizi
 * @since 2020/7/5 4:09 PM
 */
public class MasterSlaveTest extends BaseTest {

    @Test
    public void test1() {
        Jedis masterJedis = new Jedis("localhost", 16379);
        Jedis slaveJedis1 = new Jedis("localhost", 36379);
        Jedis slaveJedis2 = new Jedis("localhost", 46379);

        slaveJedis1.slaveof("localhost", 16379);
        slaveJedis2.slaveof("localhost", 16379);

        masterJedis.set("ms_k1", "v1");

        show(slaveJedis1.get("ms_k1"));
        show(slaveJedis2.get("ms_k1"));
    }

    @Test
    public void test2() {
        Jedis node = new Jedis("localhost", 36379);
        show(node.scriptLoad("return 1"));
//        show(node.watch("node_k1212", "2"));
//        show(node.move("node_k1212", 3));
//        show(node.sort("node_k1212", "1", "34"));
//        show(node.georadius("node_k121", 2.0, 3.0, 1.0, GeoUnit.FT));
//        show(node.geoadd("node_k1212", 12L, 3L, "100"));
    }
}
