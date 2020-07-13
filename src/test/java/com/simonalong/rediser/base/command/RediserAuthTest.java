package com.simonalong.rediser.base.command;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import org.junit.Test;

/**
 * @author shizi
 * @since 2020/7/13 11:42 PM
 */
public class RediserAuthTest extends BaseTest {

    @Test
    public void test1(){
        Rediser rediser = new Rediser();
        rediser.connect("localhost", 6379);
        rediser.start();

        // 需要先授权才行
        rediser.auth("test");

        show(rediser.get("k1"));
    }
}
