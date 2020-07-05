package com.simonalong.rediser.replication.core;

import com.simonalong.rediser.replication.MasterSlaveSelector;
import redis.clients.jedis.AdvancedJedisCommands;
import redis.clients.util.Slowlog;

import java.util.List;

/**
 * @author shizi
 * @since 2020/7/5 3:57 PM
 */
public interface MasterSlaveAdvancedJedisCommands extends MasterSlaveSelector, AdvancedJedisCommands {

    @Override
    default List<String> configGet(String pattern) {
        return selectMasterRediser().configGet(pattern);
    }

    @Override
    default String configSet(String parameter, String value) {
        return selectMasterRediser().configSet(parameter, value);
    }

    @Override
    default String slowlogReset() {
        return selectMasterRediser().slowlogReset();
    }

    @Override
    default Long slowlogLen() {
        return selectMasterRediser().slowlogLen();
    }

    @Override
    default List<Slowlog> slowlogGet() {
        return selectMasterRediser().slowlogGet();
    }

    @Override
    default List<Slowlog> slowlogGet(long entries) {
        return selectMasterRediser().slowlogGet(entries);
    }

    @Override
    default Long objectRefcount(String string) {
        return selectMasterRediser().objectRefcount(string);
    }

    @Override
    default String objectEncoding(String string) {
        return selectMasterRediser().objectEncoding(string);
    }

    @Override
    default Long objectIdletime(String string) {
        return selectMasterRediser().objectIdletime(string);
    }
}
