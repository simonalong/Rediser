package com.simonalong.rediser.core;

/**
 * @author shizi
 * @since 2020/7/5 5:53 PM
 */
public interface ObjectCommand {

    /**
     * 设置实体数据
     *
     * @param key   key
     * @param value 实体数据
     * @return 返回状态码：OK, null
     */
    String setObject(String key, Object value);

    /**
     * 设置实体数据
     *
     * @param key   key
     * @param value 实体数据
     * @param nxxx  只可为：nx和xx，其中大小写不敏感。nx表示不存在才进行set，xx表示只有存在才set
     * @return 返回状态码：OK, null
     */
    String setObject(String key, Object value, String nxxx);

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
    String setObject(String key, Object value, String nxxx, String expx, long time);

    /**
     * 数据不存在则设置
     *
     * @param key   key
     * @param value 实体value
     * @return 1：设置成功，0：设置失败
     */
    Integer setNxObject(final String key, final Object value);

    /**
     * 数据只有存在时候才设置
     *
     * @param key   key
     * @param value 实体value
     * @return 1：设置成功，0：设置失败
     */
    Integer setXxObject(final String key, final Object value);

    Integer set(Enum enumKey, String value, Object... params);

    /**
     * 设置数据
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   String类型的值
     * @param nxxx    只可为：nx和xx，其中大小写不敏感。nx表示不存在才进行set，xx表示只有存在才set
     * @param params  enumKey中的key属性的参数
     * @return OK：设置成功；null：设置失败
     */
    String set(Enum enumKey, String value, String nxxx, Object... params);

    /**
     * 设置数据：只有不存在才设置
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   String类型的值
     * @param params  enumKey中的key属性的参数
     * @return 1：设置成功，0：设置失败
     */
    Integer setNx(Enum enumKey, String value, Object... params);

    /**
     * 设置数据：只有存在才能设置
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   String类型的值
     * @param params  enumKey中的key属性的参数
     * @return 1：设置成功，0：设置失败
     */
    Integer setXx(Enum enumKey, String value, Object... params);


    /**
     * 设置数据：默认为setnx
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   实体数据
     * @param params  enumKey中的key属性的参数
     * @return OK：设置成功；null：设置失败
     */
    Integer setObject(Enum enumKey, Object value, Object... params);

    /**
     * 设置数据
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   实体数据
     * @param nxxx    只可为：nx和xx，其中大小写不敏感。nx表示不存在才进行set，xx表示只有存在才set
     * @param params  enumKey中的key属性的参数
     * @return OK：设置成功；null：设置失败
     */
    String setObject(Enum enumKey, Object value, String nxxx, Object... params);

    /**
     * 设置数据：只有存在才能设置
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   实体数据
     * @param params  enumKey中的key属性的参数
     * @return 1：设置成功，0：设置失败
     */
    Integer setNxObject(Enum enumKey, Object value, Object... params);

    /**
     * 设置数据：只有存在才能设置
     *
     * @param enumKey 枚举，需要是含有注解 @RediserKeyEnum 的枚举
     * @param value   实体数据
     * @param params  enumKey中的key属性的参数
     * @return 1：设置成功，0：设置失败
     */
    Integer setXxObject(Enum enumKey, Object value, Object... params);
}
