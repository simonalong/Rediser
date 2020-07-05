package com.simonalong.rediser.replication.core;

import com.simonalong.rediser.replication.MasterSlaveSelector;
import redis.clients.jedis.BasicCommands;
import redis.clients.jedis.DebugParams;

/**
 * @author shizi
 * @since 2020/7/5 4:01 PM
 */
public interface MasterSlaveBasicCommands extends MasterSlaveSelector, BasicCommands {

    @Override
    default String ping() {
        return selectMasterRediser().ping();
    }

    @Override
    default String quit() {
        return selectMasterRediser().quit();
    }

    @Override
    default String flushDB() {
        return selectMasterRediser().flushDB();
    }

    @Override
    default Long dbSize() {
        return selectMasterRediser().dbSize();
    }

    @Override
    default String select(int index) {
        return selectMasterRediser().select(index);
    }

    @Override
    default String flushAll() {
        return selectMasterRediser().flushAll();
    }

    @Override
    default String auth(String password) {
        return selectMasterRediser().auth(password);
    }

    @Override
    default String save() {
        return selectMasterRediser().save();
    }

    @Override
    default String bgsave() {
        return selectMasterRediser().bgsave();
    }

    @Override
    default String bgrewriteaof() {
        return selectMasterRediser().bgrewriteaof();
    }

    @Override
    default Long lastsave() {
        return selectMasterRediser().lastsave();
    }

    @Override
    default String shutdown() {
        return selectMasterRediser().shutdown();
    }

    @Override
    default String info() {
        return selectSlaveRediser().info();
    }

    @Override
    default String info(String section) {
        return selectSlaveRediser().info();
    }

    @Override
    default String slaveof(String host, int port) {
        return selectMasterRediser().slaveof(host, port);
    }

    @Override
    default String slaveofNoOne() {
        return selectMasterRediser().slaveofNoOne();
    }

    @Override
    default Long getDB() {
        return selectMasterRediser().getDB();
    }

    @Override
    default String debug(DebugParams params) {
        return selectMasterRediser().debug(params);
    }

    @Override
    default String configResetStat() {
        return selectMasterRediser().configResetStat();
    }

    @Override
    default Long waitReplicas(int replicas, long timeout) {
        return selectMasterRediser().waitReplicas(replicas, timeout);
    }
}
