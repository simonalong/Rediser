package com.simonalong.rediser.enums;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * 业务缓存的配置
 * @author shizi
 * @since 2020/3/14 下午1:01
 */
@RediserBusinessEnum(key="keyField", expireTime="expiredTimeField", version="versionField")
public enum BusinessCacheEnum1 {

    /**
     * 结构：ValueRedisTemplate
     * key:用户对象的unionId
     * value：用户对象的id
     */
    LK_U_I("LK_U_I_{0}",RedisTTLConstant.ONE_DAY,1.1),
    LK_U("LK_U_I_{0}",RedisTTLConstant.ONE_DAY,1.1);

    private String key;
    /**
     * 过期时间，单位：seconds
     *
     */
    private int expiredTime;
    /**
     * 版本号
     *
     */
    private double version;

    BusinessCacheEnum1(String key, int expiredTime, double version) {
        this.key = key + "_" + version;
        this.expiredTime = expiredTime;
        this.version = version;
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
