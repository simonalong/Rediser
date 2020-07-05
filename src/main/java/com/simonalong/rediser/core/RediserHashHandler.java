package com.simonalong.rediser.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shizi
 * @since 2020/3/15 下午4:20
 */
public interface RediserHashHandler extends ObjectHashHandler, JedisGetter {

    @Override
    default Long hsetObject(String key, String field, Object value) {
        return getJedis().hset(key, field, JSON.toJSONString(value));
    }

    @Override
    default <T> T hgetObject(Class<T> tClass, String key, String field) {
        return JSON.parseObject(getJedis().hget(key, field), tClass);
    }

    @Override
    default <T> List<T> hgetList(Class<T> tClass, String key, String field) {
        return JSON.parseArray(getJedis().hget(key, field), tClass);
    }

    @Override
    default JSONObject hgetMap(String key, String field) {
        return JSON.parseObject(getJedis().hget(key, field));
    }

    @Override
    default <T> Map<String, T> hgetAllObject(Class<T> tClass, String key) {
        Map<String, String> result = getJedis().hgetAll(key);
        if (null == result || result.isEmpty()) {
            return new HashMap<>(1);
        }
        return result.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> JSON.parseObject(e.getValue(), tClass)));
    }

    @Override
    default <T> Map<String, List<T>> hgetAllList(Class<T> tClass, String key) {
        Map<String, String> result = getJedis().hgetAll(key);
        if (null == result || result.isEmpty()) {
            return new HashMap<>(1);
        }
        return result.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> JSON.parseArray(e.getValue(), tClass)));
    }

}
