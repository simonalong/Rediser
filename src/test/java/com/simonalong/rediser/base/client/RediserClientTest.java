package com.simonalong.rediser.base.client;

import com.simonalong.rediser.BaseRediserTest;
import org.junit.Test;

/**
 * @author shizi
 * @since 2020/7/22 8:13 PM
 */
public class RediserClientTest extends BaseRediserTest {

    @Test
    public void test1(){
        rediser.getJedis().clientSetname("redis_client_1");
        show(rediser.getJedis().clientList());
    }
}
