package com.simonalong.rediser.base;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import com.simonalong.rediser.entity.TestEntity;
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
    public void testDistributeLock1() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.start();

        TestEntity testEntity = new TestEntity();
        testEntity.setName("name");
        testEntity.setAge(12);

        rediser.dxLock("lock_lock", 12, 12, TimeUnit.SECONDS, ()->{
            testEntity.setAge(11);
        });

        show(testEntity);
    }

    /**
     * 添加分布式锁的功能
     */
    @Test
    public void testDistributeLock2() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.start();

        TestEntity testEntity = new TestEntity();
        testEntity.setName("name");
        testEntity.setAge(12);

        show(rediser.dxLock("lock_lock", 1000, 1000, ()->{
            show(rediser.hgetAll("lock_lock"));
            testEntity.setAge(11);
            return testEntity;
        }));

        show(rediser.get("lock_lock"));
    }
}
