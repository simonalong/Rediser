package com.simonalong.rediser.jedis;

import com.simonalong.rediser.KeyBuilder;

/**
 * 针对Jedis所有默认实现的汇总
 *
 * @author shizi
 * @since 2020/3/15 上午10:54
 */
public interface DefaultJedis extends DefaultBasicCommands, DefaultJedisCommands, DefaultMultiKeyCommands, DefaultAdvancedJedisCommands, DefaultScriptingCommands, DefaultClusterCommands, DefaultSentinelCommands {

}
