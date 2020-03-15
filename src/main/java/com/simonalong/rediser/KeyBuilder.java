package com.simonalong.rediser;

import com.simonalong.rediser.annotation.RediserKeyEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shizi
 * @since 2020/3/14 下午1:57
 */
@Slf4j
@UtilityClass
public class KeyBuilder {

    /**
     * key：枚举类型的全限定路径，value为枚举类型对应的实体映射
     */
    private Map<String, KeyEnumEntity> businessEnumMap = new ConcurrentHashMap<>(8);
    private final Object lock = new Object();

    /**
     * 枚举类型解析为key
     * @param enumObject 枚举类型对应的类，添加注解：@RediserKeyEnum
     * @param keys 解析MessageFormat的key
     * @return 解析的key
     */
    public String build(Object enumObject, Object... keys) {
        validate(enumObject, keys);

        return getEnumEntity(enumObject).buildKey(keys);
    }

    private KeyEnumEntity getEnumEntity(Object object) {
        Class<?> tClass = object.getClass();
        String key = tClass.getCanonicalName() + ((Enum) object).name();
        if (businessEnumMap.containsKey(key)) {
            return businessEnumMap.get(key);
        }

        synchronized (lock) {
            if (businessEnumMap.containsKey(key)) {
                return businessEnumMap.get(key);
            }

            RediserKeyEnum rediserKeyEnum = object.getClass().getAnnotation(RediserKeyEnum.class);
            KeyEnumEntity keyEnumEntity = KeyEnumEntity.parse(object, rediserKeyEnum);
            businessEnumMap.put(key, keyEnumEntity);
            return keyEnumEntity;
        }
    }

    private void validate(Object enumObject, Object... keys) {
        Assert.assertNotNull("enumObject is null", enumObject);

        // 对象必须为枚举类型
        if (!enumObject.getClass().isEnum()) {
            throw new RuntimeException("object is not enum type");
        }

        // 对象必须包含有注解@RediserKeyEnum
        if (!enumObject.getClass().isAnnotationPresent(RediserKeyEnum.class)) {
            throw new RuntimeException("type have not annotation @RediserKeyEnum");
        }

        // format中的值不可为空
        if (Arrays.asList(keys).contains(null)) {
            throw new AssertionError("keys have null");
        }
    }

    @Setter
    @Getter
    private static class KeyEnumEntity {

        /**
         * 业务枚举中某个key的值
         */
        private String keyValue;
        /**
         * 业务枚举中某个key的过期时间，单位是毫秒
         */
        private long expireTimeValue;
        /**
         * 业务枚举中某个key对应的版本号
         */
        private String versionValue;

        static KeyEnumEntity parse(Object enumObject, RediserKeyEnum rediserKeyEnum) {
            KeyEnumEntity keyEnumEntity = new KeyEnumEntity();
            String keyFieldStr = rediserKeyEnum.keyField();
            String expireTimeFieldStr = rediserKeyEnum.expireTimeField();
            String versionFieldStr = rediserKeyEnum.versionField();

            try {
                Class<?> enumClass = enumObject.getClass();
                Field keyField = enumClass.getDeclaredField(keyFieldStr);
                Field expireTimeField = enumClass.getDeclaredField(expireTimeFieldStr);
                Field versionField = enumClass.getDeclaredField(versionFieldStr);

                keyField.setAccessible(true);
                expireTimeField.setAccessible(true);
                versionField.setAccessible(true);

                keyEnumEntity.setKeyValue((String) keyField.get(enumObject));
                keyEnumEntity.setExpireTimeValue((Long) expireTimeField.get(enumObject));
                keyEnumEntity.setVersionValue(String.valueOf(versionField.get(enumObject)));
                return keyEnumEntity;
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("not found field", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("illegal access", e);
            }
        }

        String buildKey(Object... keys) {
            return MessageFormat.format(keyValue, Arrays.stream(keys).map(String::valueOf).toArray());
        }
    }
}
