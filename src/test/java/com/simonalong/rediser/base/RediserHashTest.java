package com.simonalong.rediser.base;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import org.junit.Test;

/**
 * @author shizi
 * @since 2020/3/15 下午4:29
 */
public class RediserHashTest extends BaseTest {

    @Test
    public void testHsetGet(){
        Rediser rediser = new Rediser();
        rediser.connect("localhost:6379");
        rediser.start();

        rediser.hset("key1", "f1", "ok1");
        rediser.hset("key1", "f2", "ok2");
        rediser.hset("key1", "f3", "ok3");
        rediser.hset("key1", "f4", "ok4");
        rediser.hset("key1", "f5", "ok5");

        // ok2
        show(rediser.hget("key1", "f2"));
        // {"f1":"ok1","f2":"ok2","f3":"ok3","f4":"ok4","f5":"ok5"}
        show(rediser.hgetAll("key1"));
    }
}
