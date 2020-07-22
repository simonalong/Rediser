package com.simonalong.rediser;

import org.junit.Before;

/**
 * @author shizi
 * @since 2020/7/22 8:13 PM
 */
public class BaseRediserTest extends BaseTest {

    protected Rediser rediser = new Rediser();

    @Before
    public void before() {
        rediser.connect("localhost", 6379);
        rediser.start();
    }
}
