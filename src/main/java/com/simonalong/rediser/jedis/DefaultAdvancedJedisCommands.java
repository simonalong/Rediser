package com.simonalong.rediser.jedis;

import com.simonalong.rediser.JedisGetter;
import redis.clients.jedis.AdvancedJedisCommands;
import redis.clients.util.Slowlog;

import java.util.List;

/**
 * @author shizi
 * @since 2020/3/15 上午1:32
 */
public interface DefaultAdvancedJedisCommands extends JedisGetter, AdvancedJedisCommands {

    @Override
    default List<String> configGet(String pattern) {
        return getJedis().configGet(pattern);
    }

    @Override
    default String configSet(String parameter, String value) {
        return getJedis().configSet(parameter, value);
    }

    @Override
    default String slowlogReset() {
        return getJedis().slowlogReset();
    }

    @Override
    default Long slowlogLen() {
        return getJedis().slowlogLen();
    }

    @Override
    default List<Slowlog> slowlogGet() {
        return getJedis().slowlogGet();
    }

    @Override
    default List<Slowlog> slowlogGet(long entries) {
        return getJedis().slowlogGet(entries);
    }

    @Override
    default Long objectRefcount(String string) {
        return getJedis().objectRefcount(string);
    }

    @Override
    default String objectEncoding(String string) {
        return getJedis().objectEncoding(string);
    }

    @Override
    default Long objectIdletime(String string) {
        return getJedis().objectIdletime(string);
    }
}
