package com.simonalong.rediser.core;

import com.alibaba.fastjson.JSON;
import com.simonalong.rediser.KeyBuilder;
import javafx.util.Pair;

/**
 * @author shizi
 * @since 2020/3/15 上午10:48
 */
public interface RediserObjectCommand extends ObjectCommand, JedisGetter {

    /**
     * 设置实体数据
     *
     * @param key   key
     * @param value 实体数据
     * @return 返回状态码：OK, null
     */
    @Override
    default String setObject(String key, Object value) {
        return getJedis().set(key, JSON.toJSONString(value));
    }

    /**
     * 设置实体数据
     *
     * @param key   key
     * @param value 实体数据
     * @param nxxx  只可为：nx和xx，其中大小写不敏感。nx表示不存在才进行set，xx表示只有存在才set
     * @return 返回状态码：OK, null
     */
    @Override
    default String setObject(String key, Object value, String nxxx) {
        return getJedis().set(key, JSON.toJSONString(value), nxxx);
    }

    /**
     * 设置实体数据
     *
     * @param key   key
     * @param value 实体value
     * @param nxxx  只可为：nx和xx，其中大小写不敏感。nx表示不存在才进行set，xx表示只有存在才set
     * @param expx  只可为：ex和px，其中大消息不敏感。ex表示秒，px表示毫秒
     * @param time  过期时间
     * @return 返回状态码：OK, null
     */
    @Override
    default String setObject(String key, Object value, String nxxx, String expx, long time) {
        return getJedis().set(key, JSON.toJSONString(value), nxxx, expx, time);
    }

    /**
     * 数据不存在则设置
     *
     * @param key   key
     * @param value 实体value
     * @return 1：设置成功，0：设置失败
     */
    @Override
    default Integer setNxObject(final String key, final Object value) {
        return getJedis().setnx(key, JSON.toJSONString(value)).intValue();
    }

    /**
     * 数据只有存在时候才设置
     *
     * @param key   key
     * @param value 实体value
     * @return 1：设置成功，0：设置失败
     */
    @Override
    default Integer setXxObject(final String key, final Object value) {
        String result = getJedis().set(key, JSON.toJSONString(value), "xx");
        return (null == result) ? 0 : 1;
    }

    @Override
    default Integer set(Enum enumKey, String value, Object... params) {
        Pair<String, Long> keyAndExpire = KeyBuilder.build(enumKey, params);
        long expireTime = keyAndExpire.getValue();
        String result;
        if (0 == expireTime) {
            result = getJedis().set(keyAndExpire.getKey(), value);
        } else {
            result = getJedis().set(keyAndExpire.getKey(), value, "nx", "px", keyAndExpire.getValue());
        }
        return (null == result) ? 0 : 1;
    }

    /**
     * 设置数据
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   String类型的值
     * @param nxxx    只可为：nx和xx，其中大小写不敏感。nx表示不存在才进行set，xx表示只有存在才set
     * @param params  enumKey中的key属性的参数
     * @return OK：设置成功；null：设置失败
     */
    @Override
    default String set(Enum enumKey, String value, String nxxx, Object... params) {
        Pair<String, Long> keyAndExpire = KeyBuilder.build(enumKey, params);
        return getJedis().set(keyAndExpire.getKey(), value, nxxx, "px", keyAndExpire.getValue());
    }

    /**
     * 设置数据：只有不存在才设置
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   String类型的值
     * @param params  enumKey中的key属性的参数
     * @return 1：设置成功，0：设置失败
     */
    @Override
    default Integer setNx(Enum enumKey, String value, Object... params) {
        String result = set(enumKey, value, "nx", params);
        return (null == result) ? 0 : 1;
    }

    /**
     * 设置数据：只有存在才能设置
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   String类型的值
     * @param params  enumKey中的key属性的参数
     * @return 1：设置成功，0：设置失败
     */
    @Override
    default Integer setXx(Enum enumKey, String value, Object... params) {
        String result = set(enumKey, value, "xx", params);
        return (null == result) ? 0 : 1;
    }


    /**
     * 设置数据：默认为setnx
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   实体数据
     * @param params  enumKey中的key属性的参数
     * @return OK：设置成功；null：设置失败
     */
    @Override
    default Integer setObject(Enum enumKey, Object value, Object... params) {
        Pair<String, Long> keyAndExpire = KeyBuilder.build(enumKey, params);
        long expireTime = keyAndExpire.getValue();
        String result;
        if (0 == expireTime) {
            result = getJedis().set(keyAndExpire.getKey(), JSON.toJSONString(value));
        } else {
            result = getJedis().set(keyAndExpire.getKey(), JSON.toJSONString(value), "nx", "px", keyAndExpire.getValue());
        }
        return (null == result) ? 0 : 1;
    }

    /**
     * 设置数据
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   实体数据
     * @param nxxx    只可为：nx和xx，其中大小写不敏感。nx表示不存在才进行set，xx表示只有存在才set
     * @param params  enumKey中的key属性的参数
     * @return OK：设置成功；null：设置失败
     */
    @Override
    default String setObject(Enum enumKey, Object value, String nxxx, Object... params) {
        Pair<String, Long> keyAndExpire = KeyBuilder.build(enumKey, params);
        return getJedis().set(keyAndExpire.getKey(), JSON.toJSONString(value), nxxx, "px", keyAndExpire.getValue());
    }

    /**
     * 设置数据：只有存在才能设置
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   实体数据
     * @param params  enumKey中的key属性的参数
     * @return 1：设置成功，0：设置失败
     */
    @Override
    default Integer setNxObject(Enum enumKey, Object value, Object... params) {
        String result = setObject(enumKey, value, "nx", params);
        return (null == result) ? 0 : 1;
    }

    /**
     * 设置数据：只有存在才能设置
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   实体数据
     * @param params  enumKey中的key属性的参数
     * @return 1：设置成功，0：设置失败
     */
    @Override
    default Integer setXxObject(Enum enumKey, Object value, Object... params) {
        String result = setObject(enumKey, value, "xx", params);
        return (null == result) ? 0 : 1;
    }
}
