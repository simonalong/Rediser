package com.simonalong.rediser.replication.core;

import com.alibaba.fastjson.JSONObject;
import com.simonalong.rediser.core.ObjectQuery;
import com.simonalong.rediser.replication.MasterSlaveSelector;

import java.util.List;

/**
 * @author shizi
 * @since 2020/7/5 6:06 PM
 */
public interface MasterSlaveObjectQuery extends MasterSlaveSelector, ObjectQuery {

    @Override
    default <T> T getObject(Class<T> tClass, String key) {
        return selectSlaveRediser().getObject(tClass, key);
    }

    @Override
    default <T> List<T> getList(Class<T> tClass, String key) {
        return selectSlaveRediser().getList(tClass, key);
    }

    @Override
    default JSONObject getMap(String key) {
        return selectSlaveRediser().getMap(key);
    }

    @Override
    default String get(Enum enumKey, Object... params) {
        return selectSlaveRediser().get(enumKey, params);
    }

    @Override
    default <T> T getObject(Class<T> tClass, Enum enumKey, Object... params) {
        return selectSlaveRediser().getObject(tClass, enumKey, params);
    }

    @Override
    default <T> List<T> getList(Class<T> tClass, Enum enumKey, Object... params) {
        return selectSlaveRediser().getList(tClass, enumKey, params);
    }

    @Override
    default JSONObject getMap(Enum enumKey, Object... params) {
        return selectSlaveRediser().getMap(enumKey, params);
    }
}
