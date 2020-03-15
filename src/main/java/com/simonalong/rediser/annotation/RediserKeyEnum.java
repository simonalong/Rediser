package com.simonalong.rediser.annotation;

import java.lang.annotation.*;

/**
 * @author shizi
 * @since 2020/3/14 下午2:22
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RediserKeyEnum {

    /**
     * 枚举key格式对应的属性名
     * <p> 其中的key可以为：xxx{0}xxx{1} 这种可以通过MessageFormat进行解析的
     *
     * @return 默认 为key
     */
    String keyField() default "key";

    /**
     * 枚举的过期时间对应的属性名，默认该属性名对应的值是单位为毫秒的过期时间，对应的过期时间为0，则表示永久有效
     *
     * @return 默认 expireTime
     */
    String expireTimeField() default "expireTime";

    /**
     * 枚举的版本记录对应的属性名
     *
     * @return 默认 version
     */
    String versionField() default "version";
}
