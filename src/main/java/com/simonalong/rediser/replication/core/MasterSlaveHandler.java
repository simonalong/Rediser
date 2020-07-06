package com.simonalong.rediser.replication.core;

import com.simonalong.rediser.Rediser;
import com.simonalong.rediser.core.BaseRediser;
import com.simonalong.rediser.exception.RediserException;
import com.simonalong.rediser.replication.MasterSlaveSelector;
import com.simonalong.rediser.util.ExceptionUtil;

import java.sql.SQLTransientConnectionException;
import java.util.function.Function;

/**
 * @author shizi
 * @since 2020/7/5 11:55 PM
 */
public interface MasterSlaveHandler extends MasterSlaveSelector {

    /**
     * 内部主库调用
     *
     * @param function 回调处理
     * @param <T>      返回类型
     * @return 返回值
     */
    default <T> T doMasterCall(Function<BaseRediser, T> function) {
        Rediser rediser = selectMasterRediser();
        try {
            return function.apply(rediser);
        } catch (RediserException e) {
            deActiveMaster(rediser.getAlias());
            // todo 这里需要确认到底是哪种异常
            if (null != ExceptionUtil.getCause(e, SQLTransientConnectionException.class)) {
                return doMasterCall(function);
            }
            throw e;
        }
    }

    /**
     * 内部从库调用
     *
     * @param function 回调处理
     * @param <T>      返回类型
     * @return 返回值
     */
    default <T> T doSlaveCall(Function<BaseRediser, T> function) {
        Rediser rediser = selectSlaveRediser();
        try {
            System.out.println("rediser = " + rediser.getAlias());
            return function.apply(rediser);
        } catch (Throwable e) {
            deActiveSlave(rediser.getAlias());
            // todo 这里需要确认到底是哪种异常
            if (null != ExceptionUtil.getCause(e, SQLTransientConnectionException.class)) {
                return doSlaveCall(function);
            }
            throw e;
        }
    }
}
