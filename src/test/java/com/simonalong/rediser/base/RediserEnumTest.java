package com.simonalong.rediser.base;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.KeyBuilder;
import com.simonalong.rediser.Rediser;
import com.simonalong.rediser.entity.TestEntity;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

import static com.simonalong.rediser.enums.BusinessCacheEnum1.LK_PER;
import static com.simonalong.rediser.enums.BusinessCacheEnum1.LK_U;

/**
 * 针对业务的枚举类型读取
 *
 * @author shizi
 * @since 2020/3/14 下午1:19
 */
public class RediserEnumTest extends BaseTest {

    @Test
    public void testEnumSet() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.start();

        rediser.del(KeyBuilder.buildKey(LK_U, 12));
        rediser.set(LK_U, "asdf", 12);
        show(rediser.get(LK_U, 12));
    }

    /**
     * 测试：nx：不存在则添加数据，xx：存在则添加数据
     */
    @Test
    public void testEnumSetNxXx() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.start();

        rediser.del(LK_U, 12);
        Assert.assertEquals(rediser.set(LK_U, "value", "nx", 12), "OK");
        show(rediser.get(LK_U, 12));
        rediser.del(LK_U, 12);
        Assert.assertNull(rediser.set(LK_U, "value", "xx", 12));
        rediser.set(LK_U, "value", 12);
        Assert.assertEquals(rediser.set(LK_U, "value", "xx", 12), "OK");

        show(rediser.get(LK_U, 12));
    }

    /**
     * 测试不存在才添加数据
     */
    @Test
    public void testEnumSetNx() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.start();

        rediser.del(LK_U, 12);
        Assert.assertEquals(rediser.setnx(LK_U, "value", 12), new Integer(1));
        show(rediser.get(LK_U, 12));
    }

    /**
     * 测试存在则添加数据
     */
    @Test
    public void testEnumSetXx() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.start();

        rediser.del(LK_U, 12);

        Assert.assertEquals(rediser.setxx(LK_U, "value", 12), new Integer(0));
        rediser.setnx(LK_U, "value", 12);
        Assert.assertEquals(rediser.setxx(LK_U, "value", 12), new Integer(1));

        show(rediser.get(LK_U, 12));
    }

    @Test
    public void testEnumSetObject() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.start();

        rediser.del(LK_U, 12);

        TestEntity entity = new TestEntity();
        entity.setAge(12);
        entity.setName("nihao");

        show(rediser.setObject(LK_U, entity, 12));
        show(rediser.getObject(TestEntity.class, LK_U, 1));
        show(rediser.getObject(TestEntity.class, LK_U, 12));
    }

    @Test
    public void testEnumSetObjectNxXx() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.start();

        rediser.del(LK_U, 12);

        TestEntity entity = new TestEntity();
        entity.setAge(12);
        entity.setName("nihao");

        Assert.assertEquals(rediser.setObject(LK_U, entity, "nx", 12), "OK");
        Assert.assertNull(rediser.setObject(LK_U, entity, "nx", 12));
        show(rediser.getObject(TestEntity.class, LK_U, 12));
    }

    @Test
    public void testEnumSetObjectNx() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.start();

        rediser.del(LK_U, 12);

        TestEntity entity = new TestEntity();
        entity.setAge(12);
        entity.setName("nihao");

        Assert.assertEquals(rediser.setnxObject(LK_U, entity, 12), new Integer(1));
        Assert.assertEquals(rediser.setnxObject(LK_U, entity, 12), new Integer(0));
        show(rediser.getObject(TestEntity.class, LK_U, 12));
    }

    @Test
    public void testEnumSetObjectXx() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.start();

        rediser.del(LK_U, 12);

        TestEntity entity = new TestEntity();
        entity.setAge(12);
        entity.setName("nihao");

        Assert.assertEquals(rediser.setxxObject(LK_U, entity, 12), new Integer(0));
        rediser.setObject(LK_U, entity, 12);
        Assert.assertEquals(rediser.setxxObject(LK_U, entity, 12), new Integer(1));
        show(rediser.getObject(TestEntity.class, LK_U, 12));
    }

    /**
     * 测试过期时间
     */
    @Test
    @SneakyThrows
    public void testEnumExpireTime() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.start();

        rediser.set(LK_U, "OK", 12);
        Thread.sleep(1000);
        show(rediser.get(LK_U, 12));
        Thread.sleep(2000);
        show(rediser.get(LK_U, 12));
        Thread.sleep(1000);
        show(rediser.get(LK_U, 12));
    }

    /**
     * 测试枚举的版本号
     */
    @Test
    public void testEnumVersion() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.start();

//        rediser.set(LK_PER, "OK", 12);
        show(rediser.get(LK_PER, 12));
    }
}
