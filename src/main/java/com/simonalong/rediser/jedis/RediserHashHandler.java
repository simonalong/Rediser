package com.simonalong.rediser.jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shizi
 * @since 2020/3/15 下午4:20
 */
public interface RediserHashHandler extends JedisGetter {

    default Long hsetObject(String key, String field, Object value) {
        return getJedis().hset(key, field, JSON.toJSONString(value));
    }

    default <T> T hgetObject(Class<T> tClass, String key, String field) {
        return JSON.parseObject(getJedis().hget(key, field), tClass);
    }

    default <T> List<T> hgetList(Class<T> tClass, String key, String field) {
        return JSON.parseArray(getJedis().hget(key, field), tClass);
    }

    default JSONObject hgetMap(String key, String field) {
        return JSON.parseObject(getJedis().hget(key, field));
    }


    default <T> Map<String, T> hgetAllObject(Class<T> tClass, String key) {
        Map<String, String> result = getJedis().hgetAll(key);
        if (null == result || result.isEmpty()) {
            return new HashMap<>(1);
        }
        return result.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> JSON.parseObject(e.getValue(), tClass)));
    }

    default <T> Map<String, List<T>> hgetAllList(Class<T> tClass, String key) {
        Map<String, String> result = getJedis().hgetAll(key);
        if (null == result || result.isEmpty()) {
            return new HashMap<>(1);
        }
        return result.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> JSON.parseArray(e.getValue(), tClass)));
    }

}
