package com.simonalong.rediser.core;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author shizi
 * @since 2020/7/5 5:53 PM
 */
public interface ObjectHashHandler {

    Long hsetObject(String key, String field, Object value);

    <T> T hgetObject(Class<T> tClass, String key, String field);

    <T> List<T> hgetList(Class<T> tClass, String key, String field);

    JSONObject hgetMap(String key, String field);

    <T> Map<String, T> hgetAllObject(Class<T> tClass, String key);

    <T> Map<String, List<T>> hgetAllList(Class<T> tClass, String key);
}
