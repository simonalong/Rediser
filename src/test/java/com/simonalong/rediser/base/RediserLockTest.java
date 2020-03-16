package com.simonalong.rediser.base;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import com.simonalong.rediser.entity.TestEntity;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.ThreadPoolExecutor;
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

        rediser.dxLock("lock_lock", 12, 12, TimeUnit.SECONDS, () -> {
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

        show(rediser.dxLock("lock_lock", 1000, 1000, () -> {
            show(rediser.hgetAll("lock_lock"));
            testEntity.setAge(11);
            return testEntity;
        }));

        show(rediser.get("lock_lock"));
    }

    /**
     * 测试多线程的加锁
     */
    @Test
    @SneakyThrows
    public void testMultiThread() {
        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(new InnerTask(i + ""));
            thread.start();
        }

        Thread.sleep(1000 * 1000);
    }

    /**
     * 测试分布式锁的可重入
     */
    @Test
    @SneakyThrows
    public void testReentrant() {
        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(new InnerTaskReentrant(i + ""));
            thread.start();
        }

        Thread.sleep(1000 * 1000);
    }

    @Data
    class InnerTask implements Runnable {

        private String name;
        private Rediser rediser = Rediser.getInstance();

        InnerTask(String name) {
            rediser.bind("localhost:6379");
            rediser.start();
            this.name = name;
        }

        @Override
        @SneakyThrows
        public void run() {
            String lock = "lock_lock";
            while (true) {
                rediser.dxLock(lock, 7, 3700, () -> {
                    show(name + "加锁成功 1");
                    return null;
                }, () -> {
                    show(name + "加锁失败 1");
                    return null;
                });

                Thread.sleep(690);
            }
        }
    }

    @Data
    class InnerTaskReentrant implements Runnable {

        private String name;
        private Rediser rediser = Rediser.getInstance();

        InnerTaskReentrant(String name) {
            rediser.bind("localhost:6379");
            rediser.start();
            this.name = name;
        }

        @Override
        @SneakyThrows
        public void run() {
            String lock = "lock_lock";
            while (true) {
                rediser.dxLock(lock, 7, 3700, () -> {
                    show(name + "加锁成功 1");
                    rediser.dxLock(lock, 7, 3700, () -> {
                        show(rediser.hgetAll(lock));
                        show(name + "加锁成功 2");
                        return null;
                    }, () -> {
                        show(rediser.hgetAll(lock));
                        show(name + "加锁失败 2");
                        return null;
                    });
                    return null;
                }, () -> {
                    show(rediser.hgetAll(lock));
                    show(name + "加锁失败 1");
                    return null;
                });

                Thread.sleep(690);
            }
        }
    }
}
