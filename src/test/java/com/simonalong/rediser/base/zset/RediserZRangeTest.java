package com.simonalong.rediser.base.zset;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.Rediser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shizi
 * @since 2020/7/22 5:49 PM
 */
public class RediserZRangeTest extends BaseTest {

    Rediser rediser = new Rediser();

    @Before
    public void before() {
        rediser.connect("localhost", 6379);
        rediser.start();
    }

    @Test
    public void test1() {
        rediser.zadd("k1", 12, "a");
        show(rediser.zrange("k1", 0, -1));
        show(rediser.zrangeWithScores("k1", 0, -1));
    }

    @Test
    public void test2() {
        for (int i = 0; i < 16; i++) {
            rediser.zadd("k1", i, i + "_str");
        }

        show(rediser.zrangeByScore("k1", 0, 100, 0, 2));
    }
}
