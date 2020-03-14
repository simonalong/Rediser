package com.simonalong.rediser.enums;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author shizi
 * @since 2020/3/14 下午1:26
 */
@BusinessEnum(key="keyField", expireTime="expiredTimeField", version="versionField")
public class BusinessCacheEnum2 {

    /**
     * 结构：ValueRedisTemplate
     * key:用户对象的unionId
     * value：用户对象的id
     */
    LK_U_I("LK_U_I_{0}",RedisTTLConstant.ONE_DAY,1.1),
    LK_U("LK_U_I_{0}",RedisTTLConstant.ONE_DAY,1.1);

    private String keyField;
    /**
     * 过期时间，单位：seconds
     *
     */
    private int expiredTimeField;
    /**
     * 版本号
     *
     */
    private double versionField;

    BusinessCacheEnum1(String key, int expiredTime, double version) {
        this.keyField = key + "_" + version;
        this.expiredTimeField = expiredTime;
        this.versionField = version;
    }

    /**
     * 生成redis key
     *
     * @since qlchat
     */
    public String buildKey(Object... keys) {
        return MessageFormat.format(this.getKey(), Arrays.stream(keys).map(String::valueOf).toArray());
    }
}
