package com.simonalong.rediser.jedis;

import redis.clients.jedis.SentinelCommands;

import java.util.List;
import java.util.Map;

/**
 * @author shizi
 * @since 2020/3/15 上午3:13
 */
public interface DefaultSentinelCommands extends JedisGetter, SentinelCommands {

    @Override
    default List<Map<String, String>> sentinelMasters() {
        return getJedis().sentinelMasters();
    }

    @Override
    default List<String> sentinelGetMasterAddrByName(String masterName) {
        return getJedis().sentinelGetMasterAddrByName(masterName);
    }

    @Override
    default Long sentinelReset(String pattern) {
        return getJedis().sentinelReset(pattern);
    }

    @Override
    default List<Map<String, String>> sentinelSlaves(String masterName) {
        return getJedis().sentinelSlaves(masterName);
    }

    @Override
    default String sentinelFailover(String masterName) {
        return getJedis().sentinelFailover(masterName);
    }

    @Override
    default String sentinelMonitor(String masterName, String ip, int port, int quorum) {
        return getJedis().sentinelMonitor(masterName, ip, port, quorum);
    }

    @Override
    default String sentinelRemove(String masterName) {
        return getJedis().sentinelRemove(masterName);
    }

    @Override
    default String sentinelSet(String masterName, Map<String, String> parameterMap) {
        return getJedis().sentinelSet(masterName, parameterMap);
    }
}
