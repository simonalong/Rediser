package com.simonalong.rediser.base;

import com.simonalong.rediser.BaseTest;
import com.simonalong.rediser.cluster.ClusterRediser;
import com.simonalong.rediser.cluster.RediserClusterConfig;
import org.junit.Test;

/**
 * @author shizi
 * @since 2020/3/14 下午12:49
 */
public class ClusterRediserTest extends BaseTest {

    /**
     * 测试链接Redis集群方式
     */
    @Test
    public void testCluster() {
        ClusterRediser rediser = ClusterRediser.getInstance();
        rediser.bind("localhost:6379");
        rediser.bind("localhost:6380");
        rediser.bind("localhost:6381");
        rediser.bind("localhost:6382");
        rediser.bind("localhost:6383");
        rediser.bind("localhost:6384");

        RediserClusterConfig config = new RediserClusterConfig();
        config.setMaxAttempts(1000);
        rediser.setRediserClusterConfig(config);

        rediser.start();

        rediser.set("rediser", "testCreate");
        show(rediser.get("rediser"));
    }
}
