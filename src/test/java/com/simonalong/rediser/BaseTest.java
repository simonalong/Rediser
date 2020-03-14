package com.simonalong.rediser;

import com.alibaba.fastjson.JSON;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author shizi
 * @since 2020/3/14 上午11:39
 */
@RunWith(JUnit4.class)
public abstract class BaseTest {

    protected void show(Object object) {
        if (null == object) {
            show("object is null");
        } else {
            System.out.println(JSON.toJSONString(object));
        }
    }
}
