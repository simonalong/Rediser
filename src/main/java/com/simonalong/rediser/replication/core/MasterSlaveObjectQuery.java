package com.simonalong.rediser.replication.core;

import com.alibaba.fastjson.JSONObject;
import com.simonalong.rediser.core.ObjectQuery;
import com.simonalong.rediser.replication.MasterSlaveSelector;

import java.util.List;

/**
 * @author shizi
 * @since 2020/7/5 6:06 PM
 */
public interface MasterSlaveObjectQuery extends MasterSlaveHandler, MasterSlaveSelector, ObjectQuery {

    @Override
    default <T> T getObject(Class<T> tClass, String key) {
        return doSlaveCall(rediser -> rediser.getObject(tClass, key));
    }

    @Override
    default <T> List<T> getList(Class<T> tClass, String key) {
        return doSlaveCall(rediser -> rediser.getList(tClass, key));
    }

    @Override
    default JSONObject getMap(String key) {
        return doSlaveCall(rediser -> rediser.getMap(key));
    }

    @Override
    default String get(Enum enumKey, Object... params) {
        return doSlaveCall(rediser -> rediser.get(enumKey, params));
    }

    @Override
    default <T> T getObject(Class<T> tClass, Enum enumKey, Object... params) {
        return doSlaveCall(rediser -> rediser.getObject(tClass, enumKey, params));
    }

    @Override
    default <T> List<T> getList(Class<T> tClass, Enum enumKey, Object... params) {
        return doSlaveCall(rediser -> rediser.getList(tClass, enumKey, params));
    }

    @Override
    default JSONObject getMap(Enum enumKey, Object... params) {
        return doSlaveCall(rediser -> rediser.getMap(enumKey, params));
    }
}
