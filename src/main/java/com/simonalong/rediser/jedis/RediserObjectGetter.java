package com.simonalong.rediser.jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simonalong.rediser.KeyBuilder;

import java.util.List;

/**
 * @author shizi
 * @since 2020/3/15 上午10:52
 */
public interface RediserObjectGetter extends JedisGetter {

    default <T> T getObject(Class<T> tClass, String key) {
        return JSON.parseObject(getJedis().get(key), tClass);
    }

    default <T> List<T> getList(Class<T> tClass, String key) {
        return JSON.parseArray(getJedis().get(key), tClass);
    }

    default JSONObject getMap(String key) {
        return JSON.parseObject(getJedis().get(key));
    }

    default String get(Enum enumKey, Object... params) {
        return getJedis().get(KeyBuilder.buildKey(enumKey, params));
    }

    default <T> T getObject(Class<T> tClass, Enum enumKey, Object... params) {
        return getObject(tClass, KeyBuilder.buildKey(enumKey, params));
    }

    default <T> List<T> getList(Class<T> tClass, Enum enumKey, Object... params) {
        return getList(tClass, KeyBuilder.buildKey(enumKey, params));
    }

    default JSONObject getMap(Enum enumKey, Object... params) {
        return getMap(KeyBuilder.buildKey(enumKey, params));
    }
}
