package com.simonalong.rediser;

import com.simonalong.rediser.entity.TestEntity;
import com.simonalong.rediser.enums.BusinessCacheEnum1;
import com.simonalong.rediser.enums.BusinessCacheEnum2;
import org.junit.Test;

import static com.simonalong.rediser.enums.BusinessCacheEnum1.LK_U;

/**
 * 针对业务的枚举类型读取
 *
 * @author shizi
 * @since 2020/3/14 下午1:19
 */
public class RediserEnumTest {

    @Test
    public void testEnum1(){
        Rediser rediser = Rediser.getInstantce();
        rediser.bind("localhost:6379");
        // 添加业务类型
        rediser.addBusinessEnum(BusinessCacheEnum1.class);
        rediser.addBusinessEnum(BusinessCacheEnum2.class);

        TestEntity testEntity = new TestEntity();
        testEntity.setName("ok");
        testEntity.setAge(12);
        rediser.set(LK_U.buildKey("12"), testEntity);

        TestEntity result = rediser.get(LK_U.buildKey("12"), TestEntity.class);
        show(result);
    }
}
