package com.simonalong.rediser.base;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.KeyBuilder;
import com.simonalong.rediser.Rediser;
import com.simonalong.rediser.entity.TestEntity;
import org.junit.Test;

import static com.simonalong.rediser.enums.BusinessCacheEnum1.LK_U;

/**
 * 针对业务的枚举类型读取
 *
 * @author shizi
 * @since 2020/3/14 下午1:19
 */
public class RediserEnumTest extends BaseTest {

    @Test
    public void testEnum1() {
        Rediser rediser = Rediser.getInstance();
        rediser.connect("localhost:6379");
        rediser.start();

        TestEntity testEntity = new TestEntity();
        testEntity.setName("ok");
        testEntity.setAge(12);
        rediser.set(KeyBuilder.build(LK_U, 12), testEntity);

        show(rediser.get(KeyBuilder.build(LK_U, 12), TestEntity.class));
        show(rediser.get(KeyBuilder.build(LK_U, 1), TestEntity.class));
    }
}
