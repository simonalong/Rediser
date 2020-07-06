package com.simonalong.rediser.replication;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author shizi
 * @since 2020/7/5 6:25 PM
 */
@Slf4j
public class MasterSlaveRediserTest extends BaseTest {

    @Test
    public void test1(){
        MasterSlaveRediser rediser = MasterSlaveRediser.getInstance();
        rediser.setMasterNode("localhost", 16379);
        rediser.addSlaveNode("localhost", 36379);
        rediser.addSlaveNode("localhost", 46379);
        rediser.start();

        rediser.set("kk", "ok");
        show(rediser.get("kk"));
    }

    @Test
    @SneakyThrows
    public void test2(){
        MasterSlaveRediser rediser = MasterSlaveRediser.getInstance();
        rediser.setMasterNode("localhost", 16379);
        rediser.addSlaveNode("localhost", 36379);
        rediser.addSlaveNode("localhost", 46379);
        rediser.start();

        rediser.set("kk1", "ok1");
        show(rediser.get("kk1"));
        show(rediser.get("kk1"));
        show(rediser.get("kk1"));
        show(rediser.get("kk1"));
        Thread.sleep(10 * 1000);
        show(rediser.get("kk1"));
        show(rediser.get("kk1"));
        show(rediser.get("kk1"));
        show(rediser.get("kk1"));
        show(rediser.get("kk1"));
        show(rediser.get("kk1"));
    }

}
