package com.simonalong.rediser.replication;

import com.simonalong.rediser.Rediser;

/**
 * @author shizi
 * @since 2020/7/5 3:35 PM
 */
public interface MasterSlaveSelector {

    /**
     * 获取主库Redis
     *
     * @return 主库Redis
     */
    Rediser selectMasterRediser();

    /**
     * 获取从库Redis
     *
     * @return 从库Redis
     */
    Rediser selectSlaveRediser();

    /**
     * 去激活主库
     *
     * @param alias 主库名
     */
    void deActiveMaster(String alias);

    /**
     * 去激活从库
     *
     * @param alias 从库名
     */
    void deActiveSlave(String alias);
}
