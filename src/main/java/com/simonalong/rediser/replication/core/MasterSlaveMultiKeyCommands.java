package com.simonalong.rediser.replication.core;

import com.simonalong.rediser.replication.MasterSlaveSelector;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Set;

/**
 * @author shizi
 * @since 2020/7/5 5:21 PM
 */
public interface MasterSlaveMultiKeyCommands extends MasterSlaveHandler, MasterSlaveSelector, MultiKeyCommands {

    @Override
    default Long del(String... keys) {
        return doMasterCall(rediser -> rediser.del(keys));
    }

    @Override
    default Long exists(String... keys) {
        return doSlaveCall(rediser -> rediser.exists(keys));
    }

    @Override
    default List<String> blpop(int timeout, String... keys) {
        return doMasterCall(rediser -> rediser.blpop(timeout, keys));
    }

    @Override
    default List<String> brpop(int timeout, String... keys) {
        return doMasterCall(rediser -> rediser.brpop(timeout, keys));
    }

    @Override
    default List<String> blpop(String... args) {
        return doMasterCall(rediser -> rediser.blpop(args));
    }

    @Override
    default List<String> brpop(String... args) {
        return doMasterCall(rediser -> rediser.brpop(args));
    }

    @Override
    default Set<String> keys(String pattern) {
        return doSlaveCall(rediser -> rediser.keys(pattern));
    }

    @Override
    default List<String> mget(String... keys) {
        return doSlaveCall(rediser -> rediser.mget(keys));
    }

    @Override
    default String mset(String... keysvalues) {
        return doMasterCall(rediser -> rediser.mset(keysvalues));
    }

    @Override
    default Long msetnx(String... keysvalues) {
        return doMasterCall(rediser -> rediser.msetnx(keysvalues));
    }

    @Override
    default String rename(String oldkey, String newkey) {
        return doMasterCall(rediser -> rediser.rename(oldkey, newkey));
    }

    @Override
    default Long renamenx(String oldkey, String newkey) {
        return doMasterCall(rediser -> rediser.renamenx(oldkey, newkey));
    }

    @Override
    default String rpoplpush(String srckey, String dstkey) {
        return doMasterCall(rediser -> rediser.rpoplpush(srckey, dstkey));
    }

    @Override
    default Set<String> sdiff(String... keys) {
        return doSlaveCall(rediser -> rediser.sdiff(keys));
    }

    @Override
    default Long sdiffstore(String dstkey, String... keys) {
        return doMasterCall(rediser -> rediser.sdiffstore(dstkey, keys));
    }

    @Override
    default Set<String> sinter(String... keys) {
        return doSlaveCall(rediser -> rediser.sinter(keys));
    }

    @Override
    default Long sinterstore(String dstkey, String... keys) {
        return doSlaveCall(rediser -> rediser.sinterstore(dstkey, keys));
    }

    @Override
    default Long smove(String srckey, String dstkey, String member) {
        return doMasterCall(rediser -> rediser.smove(srckey, dstkey, member));
    }

    @Override
    default Long sort(String key, SortingParams sortingParameters, String dstkey) {
        return doMasterCall(rediser -> rediser.sort(key, sortingParameters, dstkey));
    }

    @Override
    default Long sort(String key, String dstkey) {
        return doMasterCall(rediser -> rediser.sort(key, dstkey));
    }

    @Override
    default Set<String> sunion(String... keys) {
        return doMasterCall(rediser -> rediser.sunion(keys));
    }

    @Override
    default Long sunionstore(String dstkey, String... keys) {
        return doMasterCall(rediser -> rediser.sunionstore(dstkey, keys));
    }

    @Override
    default String watch(String... keys) {
        return doMasterCall(rediser -> rediser.watch(keys));
    }

    @Override
    default String unwatch() {
        return doMasterCall(rediser -> rediser.unwatch());
    }

    @Override
    default Long zinterstore(String dstkey, String... sets) {
        return doMasterCall(rediser -> rediser.zinterstore(dstkey, sets));
    }

    @Override
    default Long zinterstore(String dstkey, ZParams params, String... sets) {
        return doMasterCall(rediser -> rediser.zinterstore(dstkey, params, sets));
    }

    @Override
    default Long zunionstore(String dstkey, String... sets) {
        return doMasterCall(rediser -> rediser.zunionstore(dstkey, sets));
    }

    @Override
    default Long zunionstore(String dstkey, ZParams params, String... sets) {
        return doMasterCall(rediser -> rediser.zunionstore(dstkey, params, sets));
    }

    @Override
    default String brpoplpush(String source, String destination, int timeout) {
        return doMasterCall(rediser -> rediser.brpoplpush(source, destination, timeout));
    }

    @Override
    default Long publish(String channel, String message) {
        return doMasterCall(rediser -> rediser.publish(channel, message));
    }

    @Override
    default void subscribe(JedisPubSub jedisPubSub, String... channels) {
        doMasterCall(rediser -> {
            rediser.subscribe(jedisPubSub, channels);
            return null;
        });
    }

    @Override
    default void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
        doMasterCall(rediser -> {
            rediser.psubscribe(jedisPubSub, patterns);
            return null;
        });
    }

    @Override
    default String randomKey() {
        return doMasterCall(rediser -> rediser.randomKey());
    }

    @Override
    default Long bitop(BitOP op, String destKey, String... srcKeys) {
        return doMasterCall(rediser -> rediser.bitop(op, destKey, srcKeys));
    }

    @Override
    default ScanResult<String> scan(int cursor) {
        return doSlaveCall(rediser -> rediser.scan(cursor));
    }

    @Override
    default ScanResult<String> scan(String cursor) {
        return doSlaveCall(rediser -> rediser.scan(cursor));
    }

    @Override
    default ScanResult<String> scan(String cursor, ScanParams params) {
        return doSlaveCall(rediser -> rediser.scan(cursor, params));
    }

    @Override
    default String pfmerge(String destkey, String... sourcekeys) {
        return doMasterCall(rediser -> rediser.pfmerge(destkey, sourcekeys));
    }

    @Override
    default long pfcount(String... keys) {
        return doSlaveCall(rediser -> rediser.pfcount(keys));
    }
}
