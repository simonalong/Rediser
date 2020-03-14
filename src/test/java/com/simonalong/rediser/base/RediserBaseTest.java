package com.simonalong.rediser.base;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import org.junit.Test;

/**
 * @author shizi
 * @since 2020/3/14 下午12:48
 */
public class RediserBaseTest extends BaseTest {

    /**
     * 测试创建和绑定，方式1
     */
    @Test
    public void testCreate1() {
        Rediser rediser = Rediser.getInstance();
        rediser.connect("localhost", 6379);
        rediser.start();

        rediser.set("rediser", "testCreate");
        show(rediser.get("rediser"));
    }

    /**
     * 测试创建和绑定，方式2
     */
    @Test
    public void testCreate2() {
        Rediser rediser = Rediser.getInstance();
        rediser.connect("localhost:6379");
        rediser.start();
        rediser.set("rediser", "testCreate");
        show(rediser.get("rediser"));
    }
}
