package com.simonalong.rediser.core;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author shizi
 * @since 2020/7/5 5:53 PM
 */
public interface ObjectQuery {

    <T> T getObject(Class<T> tClass, String key);

    <T> List<T> getList(Class<T> tClass, String key);

    JSONObject getMap(String key);

    String get(Enum enumKey, Object... params);

    <T> T getObject(Class<T> tClass, Enum enumKey, Object... params);

    <T> List<T> getList(Class<T> tClass, Enum enumKey, Object... params);

    JSONObject getMap(Enum enumKey, Object... params);
}
