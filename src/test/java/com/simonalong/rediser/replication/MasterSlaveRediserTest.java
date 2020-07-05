package com.simonalong.rediser.replication;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import lombok.SneakyThrows;
import org.junit.Test;

/**
 * @author shizi
 * @since 2020/7/5 6:25 PM
 */
public class MasterSlaveRediserTest extends BaseTest {

    @Test
    @SneakyThrows
    public void test1(){
//        MasterSlaveRediser rediser = MasterSlaveRediser.getInstance();
//        rediser.setMasterNode("localhost", 16379);
//        rediser.addSlaveNode("localhost", 36379);
//        rediser.addSlaveNode("localhost", 46379);
//        rediser.start();
//
//        rediser.set("k1", "v1");
//        Thread.sleep(10 * 1000);
//        show(rediser.get("k1"));

        Rediser node = new Rediser();
        node.connect("localhost", 46379);
        node.start();

        show(node.get("k1"));
    }
}
