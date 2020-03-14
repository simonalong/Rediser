package com.simonalong.rediser.base;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import org.junit.Test;

/**
 * 多级缓存测试
 *
 * @author shizi
 * @since 2020/3/14 下午12:48
 */
public class RediserMultiCacheTest extends BaseTest {

    /**
     * 测试本机缓存
     * <p>不配置分布式缓存
     */
    @Test
    public void tesLocalCache() {
        Rediser rediser = Rediser.getInstance();
        rediser.setLocal("key", "value");
        show(rediser.getLocal("key"));
    }

    /**
     * 多级缓存
     * <p>包含本机缓存，也包含分布式缓存。如果本机缓存没有则会向分布式缓存获取
     */
    @Test
    public void tesLocalCache2() {
        Rediser rediser = Rediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.setLocal("key", "value");
        show(rediser.getLocal("key"));
    }
}
