package com.simonalong.rediser.core;

import com.simonalong.rediser.KeyBuilder;

/**
 * 针对Jedis所有默认实现的汇总
 *
 * @author shizi
 * @since 2020/3/15 上午10:54
 */
public interface DefaultJedis extends DefaultBasicCommands, DefaultJedisCommands, DefaultMultiKeyCommands, DefaultAdvancedJedisCommands, DefaultScriptingCommands {

    default Long del(Enum enumKey, Object... params) {
        return getJedis().del(KeyBuilder.buildKey(enumKey, params));
    }
}
