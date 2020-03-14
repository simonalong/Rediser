package com.simonalong.rediser.enums;

import com.simonalong.rediser.annotation.RediserKeyEnum;

import java.util.concurrent.TimeUnit;

/**
 * 业务缓存的配置
 *
 * @author shizi
 * @since 2020/3/14 下午1:01
 */
@RediserKeyEnum(keyField = "key", expireTimeField = "expiredTime", versionField = "version")
public enum BusinessCacheEnum1 {

    /**
     * 结构：ValueRedisTemplate
     * key:用户对象的unionId
     * value：用户对象的id
     */
    LK_U_I("LK_U_I_{0}", TimeUnit.DAYS, 1.1),
    /**
     * 结构：ValueRedisTemplate
     * key:用户对象的unionId
     * value：用户对象的id
     * 过期：3秒钟过期
     */
    LK_U("LK_U_I_{0}", 3, TimeUnit.SECONDS, 1.1);

    private String key;
    /**
     * 过期时间，单位：seconds。如果不设置则默认为10秒
     */
    private long expiredTime = 10 * 1000;
    /**
     * 版本号
     */
    private double version = 1.0;


    /**
     * 过期单位不设置，默认为1
     *
     * @param key       key
     * @param timeUnite 过期单位
     */
    BusinessCacheEnum1(String key, TimeUnit timeUnite) {
        this.key = key;
        this.expiredTime = timeUnite.toMillis(1);
        this.version = version;
    }

    BusinessCacheEnum1(String key, TimeUnit timeUnite, double version) {
        this.key = key;
        this.expiredTime = timeUnite.toMillis(1);
        this.version = version;
    }

    BusinessCacheEnum1(String key, int expiredTime, TimeUnit timeUnite) {
        this.key = key;
        this.expiredTime = timeUnite.toMillis(expiredTime);
    }

    BusinessCacheEnum1(String key, int expiredTime, TimeUnit timeUnite, double version) {
        this.key = key;
        this.expiredTime = timeUnite.toMillis(expiredTime);
        this.version = version;
    }
}
