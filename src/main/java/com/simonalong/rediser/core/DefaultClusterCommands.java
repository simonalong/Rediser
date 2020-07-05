package com.simonalong.rediser.core;

import redis.clients.jedis.ClusterCommands;
import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * @author shizi
 * @since 2020/3/15 上午3:12
 */
public interface DefaultClusterCommands extends JedisGetter, ClusterCommands {

    @Override
    default String clusterNodes() {
        return getJedis().clusterNodes();
    }

    @Override
    default String clusterMeet(String ip, int port) {
        return getJedis().clusterMeet(ip, port);
    }

    @Override
    default String clusterAddSlots(int... slots) {
        return getJedis().clusterAddSlots(slots);
    }

    @Override
    default String clusterDelSlots(int... slots) {
        return getJedis().clusterDelSlots(slots);
    }

    @Override
    default String clusterInfo() {
        return getJedis().clusterInfo();
    }

    @Override
    default List<String> clusterGetKeysInSlot(int slot, int count) {
        return getJedis().clusterGetKeysInSlot(slot, count);
    }

    @Override
    default String clusterSetSlotNode(int slot, String nodeId) {
        return getJedis().clusterSetSlotNode(slot, nodeId);
    }

    @Override
    default String clusterSetSlotMigrating(int slot, String nodeId) {
        return getJedis().clusterSetSlotMigrating(slot, nodeId);
    }

    @Override
    default String clusterSetSlotImporting(int slot, String nodeId) {
        return getJedis().clusterSetSlotImporting(slot, nodeId);
    }

    @Override
    default String clusterSetSlotStable(int slot) {
        return getJedis().clusterSetSlotStable(slot);
    }

    @Override
    default String clusterForget(String nodeId) {
        return getJedis().clusterForget(nodeId);
    }

    @Override
    default String clusterFlushSlots() {
        return getJedis().clusterFlushSlots();
    }

    @Override
    default Long clusterKeySlot(String key) {
        return getJedis().clusterKeySlot(key);
    }

    @Override
    default Long clusterCountKeysInSlot(int slot) {
        return getJedis().clusterCountKeysInSlot(slot);
    }

    @Override
    default String clusterSaveConfig() {
        return getJedis().clusterSaveConfig();
    }

    @Override
    default String clusterReplicate(String nodeId) {
        return getJedis().clusterReplicate(nodeId);
    }

    @Override
    default List<String> clusterSlaves(String nodeId) {
        return getJedis().clusterSlaves(nodeId);
    }

    @Override
    default String clusterFailover() {
        return getJedis().clusterFailover();
    }

    @Override
    default List<Object> clusterSlots() {
        return getJedis().clusterSlots();
    }

    @Override
    default String clusterReset(JedisCluster.Reset resetType) {
        return getJedis().clusterReset(resetType);
    }

    @Override
    default String readonly() {
        return getJedis().readonly();
    }
}
