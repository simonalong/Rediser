package com.simonalong.rediser.base;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author shizi
 * @since 2020/3/14 下午12:50
 */
public class RediserLockTest extends BaseTest {

    /**
     * 添加分布式锁的功能
     */
    @Test
    public void testDistributeLock() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");

        try {
            rediser.lock("key", 12, TimeUnit.SECONDS);

            // do something
        } finally {
            rediser.unlock("key");
        }
    }
}
