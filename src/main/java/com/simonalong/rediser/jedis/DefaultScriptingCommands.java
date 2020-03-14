package com.simonalong.rediser.jedis;

import redis.clients.jedis.ScriptingCommands;

import java.util.List;

/**
 * @author shizi
 * @since 2020/3/15 上午3:11
 */
public interface DefaultScriptingCommands extends JedisGetter, ScriptingCommands {

    @Override
    default Object eval(String script, int keyCount, String... params) {
        return getJedis().eval(script, keyCount, params);
    }

    @Override
    default Object eval(String script, List<String> keys, List<String> args) {
        return getJedis().eval(script, keys, args);
    }

    @Override
    default Object eval(String script) {
        return getJedis().eval(script);
    }

    @Override
    default Object evalsha(String script) {
        return getJedis().evalsha(script);
    }

    @Override
    default Object evalsha(String sha1, List<String> keys, List<String> args) {
        return getJedis().evalsha(sha1);
    }

    @Override
    default Object evalsha(String sha1, int keyCount, String... params) {
        return getJedis().evalsha(sha1);
    }

    @Override
    default Boolean scriptExists(String sha1) {
        return getJedis().scriptExists(sha1);
    }

    @Override
    default List<Boolean> scriptExists(String... sha1) {
        return getJedis().scriptExists(sha1);
    }

    @Override
    default String scriptLoad(String script) {
        return getJedis().scriptLoad(script);
    }
}
