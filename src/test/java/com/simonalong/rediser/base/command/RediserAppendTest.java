package com.simonalong.rediser.base.command;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import org.junit.Test;

/**
 * @author shizi
 * @since 2020/7/12 6:27 PM
 */
public class RediserAppendTest extends BaseTest {

    @Test
    public void test1() {
        Rediser rediser = new Rediser();
        rediser.connect("localhost", 6379);
        rediser.start();

        rediser.del("k1");
        show(rediser.exists("k1"));
        rediser.set("k1", "value1");
        show(rediser.append("k1", "-v-append"));
        show(rediser.get("k1"));
    }
}
