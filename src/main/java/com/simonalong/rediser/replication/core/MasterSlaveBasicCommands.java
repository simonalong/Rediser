package com.simonalong.rediser.replication.core;

import com.simonalong.rediser.core.DefaultBasicCommands;
import com.simonalong.rediser.replication.MasterSlaveSelector;
import redis.clients.jedis.BasicCommands;
import redis.clients.jedis.DebugParams;

/**
 * @author shizi
 * @since 2020/7/5 4:01 PM
 */
public interface MasterSlaveBasicCommands extends MasterSlaveHandler, MasterSlaveSelector, BasicCommands {

    @Override
    default String ping() {
        return doMasterCall(DefaultBasicCommands::ping);
    }

    @Override
    default String quit() {
        return doMasterCall(DefaultBasicCommands::quit);
    }

    @Override
    default String flushDB() {
        return doMasterCall(DefaultBasicCommands::flushDB);
    }

    @Override
    default Long dbSize() {
        return doMasterCall(DefaultBasicCommands::dbSize);
    }

    @Override
    default String select(int index) {
        return doMasterCall(rediser -> rediser.select(index));
    }

    @Override
    default String flushAll() {
        return doMasterCall(DefaultBasicCommands::flushAll);
    }

    @Override
    default String auth(String password) {
        return doMasterCall(rediser -> rediser.auth(password));
    }

    @Override
    default String save() {
        return doMasterCall(DefaultBasicCommands::save);
    }

    @Override
    default String bgsave() {
        return doMasterCall(DefaultBasicCommands::bgsave);
    }

    @Override
    default String bgrewriteaof() {
        return doMasterCall(DefaultBasicCommands::bgrewriteaof);
    }

    @Override
    default Long lastsave() {
        return doMasterCall(DefaultBasicCommands::lastsave);
    }

    @Override
    default String shutdown() {
        return doMasterCall(DefaultBasicCommands::shutdown);
    }

    @Override
    default String info() {
        return doSlaveCall(DefaultBasicCommands::info);
    }

    @Override
    default String info(String section) {
        return doSlaveCall(DefaultBasicCommands::info);
    }

    @Override
    default String slaveof(String host, int port) {
        return doMasterCall(rediser -> rediser.slaveof(host, port));
    }

    @Override
    default String slaveofNoOne() {
        return doMasterCall(DefaultBasicCommands::slaveofNoOne);
    }

    @Override
    default Long getDB() {
        return doMasterCall(DefaultBasicCommands::getDB);
    }

    @Override
    default String debug(DebugParams params) {
        return doMasterCall(rediser -> rediser.debug(params));
    }

    @Override
    default String configResetStat() {
        return doMasterCall(DefaultBasicCommands::configResetStat);
    }

    @Override
    default Long waitReplicas(int replicas, long timeout) {
        return doMasterCall(rediser -> rediser.waitReplicas(replicas, timeout));
    }
}
