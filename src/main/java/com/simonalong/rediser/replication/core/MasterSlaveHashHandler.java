package com.simonalong.rediser.replication.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simonalong.rediser.core.ObjectHashHandler;
import com.simonalong.rediser.core.RediserHashHandler;
import com.simonalong.rediser.replication.MasterSlaveSelector;

import java.util.List;
import java.util.Map;

/**
 * @author shizi
 * @since 2020/7/5 5:51 PM
 */
public interface MasterSlaveHashHandler extends MasterSlaveHandler, MasterSlaveSelector, ObjectHashHandler {

    @Override
    default Long hsetObject(String key, String field, Object value) {
        return doMasterCall(rediser -> rediser.hset(key, field, JSON.toJSONString(value)));
    }

    @Override
    default <T> T hgetObject(Class<T> tClass, String key, String field) {
        return doSlaveCall(rediser -> rediser.hgetObject(tClass, key, field));
    }

    @Override
    default <T> List<T> hgetList(Class<T> tClass, String key, String field) {
        return doSlaveCall(rediser -> rediser.hgetList(tClass, key, field));
    }

    @Override
    default JSONObject hgetMap(String key, String field) {
        return doSlaveCall(rediser -> rediser.hgetMap(key, field));
    }

    @Override
    default <T> Map<String, T> hgetAllObject(Class<T> tClass, String key) {
        return doSlaveCall(rediser -> rediser.hgetAllObject(tClass, key));
    }

    @Override
    default <T> Map<String, List<T>> hgetAllList(Class<T> tClass, String key) {
        return doSlaveCall(rediser -> rediser.hgetAllList(tClass, key));
    }
}
