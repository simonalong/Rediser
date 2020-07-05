package com.simonalong.rediser.replication.core;

import com.simonalong.rediser.core.DefaultAdvancedJedisCommands;
import com.simonalong.rediser.replication.MasterSlaveSelector;
import redis.clients.jedis.AdvancedJedisCommands;
import redis.clients.util.Slowlog;

import java.util.List;

/**
 * @author shizi
 * @since 2020/7/5 3:57 PM
 */
public interface MasterSlaveAdvancedJedisCommands extends MasterSlaveHandler, MasterSlaveSelector, AdvancedJedisCommands {

    @Override
    default List<String> configGet(String pattern) {
        return doMasterCall(rediser -> rediser.configGet(pattern));
    }

    @Override
    default String configSet(String parameter, String value) {
        return doMasterCall(rediser -> rediser.configSet(parameter, value));
    }

    @Override
    default String slowlogReset() {
        return doMasterCall(DefaultAdvancedJedisCommands::slowlogReset);
    }

    @Override
    default Long slowlogLen() {
        return doMasterCall(DefaultAdvancedJedisCommands::slowlogLen);
    }

    @Override
    default List<Slowlog> slowlogGet() {
        return doMasterCall(DefaultAdvancedJedisCommands::slowlogGet);
    }

    @Override
    default List<Slowlog> slowlogGet(long entries) {
        return doMasterCall(rediser -> rediser.slowlogGet(entries));
    }

    @Override
    default Long objectRefcount(String string) {
        return doMasterCall(rediser -> rediser.objectRefcount(string));
    }

    @Override
    default String objectEncoding(String string) {
        return doMasterCall(rediser -> rediser.objectEncoding(string));
    }

    @Override
    default Long objectIdletime(String string) {
        return doMasterCall(rediser -> rediser.objectIdletime(string));
    }
}
