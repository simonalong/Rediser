package com.simonalong.rediser.replication;

import com.simonalong.rediser.BaseTest;
import org.junit.Test;

/**
 * @author shizi
 * @since 2020/7/5 6:25 PM
 */
public class MasterSlaveRediserTest extends BaseTest {

    @Test
    public void test1(){
        MasterSlaveRediser rediser = MasterSlaveRediser.getInstance();
        rediser.setMasterNode("localhost", 16379);
        rediser.addSlaveNode("localhost", 36379);
        rediser.addSlaveNode("localhost", 46379);
        rediser.start();

        rediser.set("k1", "v");
        rediser.get("k1");
    }
}
