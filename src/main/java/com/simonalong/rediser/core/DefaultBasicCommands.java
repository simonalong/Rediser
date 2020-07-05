package com.simonalong.rediser.core;

import redis.clients.jedis.BasicCommands;
import redis.clients.jedis.DebugParams;

/**
 * @author shizi
 * @since 2020/3/15 上午3:12
 */
public interface DefaultBasicCommands extends JedisGetter, BasicCommands {

    @Override
    default String ping() {
        return getJedis().ping();
    }

    @Override
    default String quit() {
        return getJedis().quit();
    }

    @Override
    default String flushDB() {
        return getJedis().flushDB();
    }

    @Override
    default Long dbSize() {
        return getJedis().dbSize();
    }

    @Override
    default String select(int index) {
        return getJedis().select(index);
    }

    @Override
    default String flushAll() {
        return getJedis().flushAll();
    }

    @Override
    default String auth(String password) {
        return getJedis().auth(password);
    }

    @Override
    default String save() {
        return getJedis().save();
    }

    @Override
    default String bgsave() {
        return getJedis().bgsave();
    }

    @Override
    default String bgrewriteaof() {
        return getJedis().bgrewriteaof();
    }

    @Override
    default Long lastsave() {
        return getJedis().lastsave();
    }

    @Override
    default String shutdown() {
        return getJedis().shutdown();
    }

    @Override
    default String info() {
        return getJedis().info();
    }

    @Override
    default String info(String section) {
        return getJedis().info(section);
    }

    @Override
    default String slaveof(String host, int port) {
        return getJedis().slaveof(host, port);
    }

    @Override
    default String slaveofNoOne() {
        return getJedis().slaveofNoOne();
    }

    @Override
    default Long getDB() {
        return getJedis().getDB();
    }

    @Override
    default String debug(DebugParams params) {
        return getJedis().debug(params);
    }

    @Override
    default String configResetStat() {
        return getJedis().configResetStat();
    }

    @Override
    default Long waitReplicas(int replicas, long timeout) {
        return getJedis().waitReplicas(replicas, timeout);
    }
}
