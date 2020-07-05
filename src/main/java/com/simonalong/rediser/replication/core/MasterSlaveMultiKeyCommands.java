package com.simonalong.rediser.replication.core;

import com.simonalong.rediser.replication.MasterSlaveSelector;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Set;

/**
 * @author shizi
 * @since 2020/7/5 5:21 PM
 */
public interface MasterSlaveMultiKeyCommands extends MasterSlaveSelector, MultiKeyCommands {

    @Override
    default  Long del(String... keys) {
        return selectMasterRediser().del(keys);
    }

    @Override
    default  Long exists(String... keys) {
        return selectSlaveRediser().exists(keys);
    }

    @Override
    default  List<String> blpop(int timeout, String... keys) {
        return selectMasterRediser().blpop(timeout, keys);
    }

    @Override
    default  List<String> brpop(int timeout, String... keys) {
        return selectMasterRediser().brpop(timeout, keys);
    }

    @Override
    default  List<String> blpop(String... args) {
        return selectMasterRediser().blpop(args);
    }

    @Override
    default  List<String> brpop(String... args) {
        return selectMasterRediser().brpop(args);
    }

    @Override
    default  Set<String> keys(String pattern) {
        return selectSlaveRediser().keys(pattern);
    }

    @Override
    default  List<String> mget(String... keys) {
        return selectSlaveRediser().mget(keys);
    }

    @Override
    default  String mset(String... keysvalues) {
        return selectMasterRediser().mset(keysvalues);
    }

    @Override
    default  Long msetnx(String... keysvalues) {
        return selectMasterRediser().msetnx(keysvalues);
    }

    @Override
    default  String rename(String oldkey, String newkey) {
        return selectMasterRediser().rename(oldkey, newkey);
    }

    @Override
    default  Long renamenx(String oldkey, String newkey) {
        return selectMasterRediser().renamenx(oldkey, newkey);
    }

    @Override
    default  String rpoplpush(String srckey, String dstkey) {
        return selectMasterRediser().rpoplpush(srckey, dstkey);
    }

    @Override
    default  Set<String> sdiff(String... keys) {
        return selectSlaveRediser().sdiff(keys);
    }

    @Override
    default  Long sdiffstore(String dstkey, String... keys) {
        return selectMasterRediser().sdiffstore(dstkey, keys);
    }

    @Override
    default  Set<String> sinter(String... keys) {
        return selectSlaveRediser().sinter(keys);
    }

    @Override
    default  Long sinterstore(String dstkey, String... keys) {
        return selectSlaveRediser().sinterstore(dstkey, keys);
    }

    @Override
    default  Long smove(String srckey, String dstkey, String member) {
        return selectMasterRediser().smove(srckey, dstkey, member);
    }

    @Override
    default  Long sort(String key, SortingParams sortingParameters, String dstkey) {
        return selectMasterRediser().sort(key, sortingParameters, dstkey);
    }

    @Override
    default  Long sort(String key, String dstkey) {
        return selectMasterRediser().sort(key, dstkey);
    }

    @Override
    default  Set<String> sunion(String... keys) {
        return selectMasterRediser().sunion(keys);
    }

    @Override
    default  Long sunionstore(String dstkey, String... keys) {
        return selectMasterRediser().sunionstore(dstkey, keys);
    }

    @Override
    default  String watch(String... keys) {
        return selectMasterRediser().watch(keys);
    }

    @Override
    default  String unwatch() {
        return selectMasterRediser().unwatch();
    }

    @Override
    default  Long zinterstore(String dstkey, String... sets) {
        return selectMasterRediser().zinterstore(dstkey, sets);
    }

    @Override
    default  Long zinterstore(String dstkey, ZParams params, String... sets) {
        return selectMasterRediser().zinterstore(dstkey, params, sets);
    }

    @Override
    default  Long zunionstore(String dstkey, String... sets) {
        return selectMasterRediser().zunionstore(dstkey, sets);
    }

    @Override
    default  Long zunionstore(String dstkey, ZParams params, String... sets) {
        return selectMasterRediser().zunionstore(dstkey, params, sets);
    }

    @Override
    default  String brpoplpush(String source, String destination, int timeout) {
        return selectMasterRediser().brpoplpush(source, destination, timeout);
    }

    @Override
    default  Long publish(String channel, String message) {
        return selectMasterRediser().publish(channel, message);
    }

    @Override
    default  void subscribe(JedisPubSub jedisPubSub, String... channels) {
        selectMasterRediser().subscribe(jedisPubSub, channels);
    }

    @Override
    default  void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
        selectMasterRediser().psubscribe(jedisPubSub, patterns);
    }

    @Override
    default  String randomKey() {
        return selectMasterRediser().randomKey();
    }

    @Override
    default  Long bitop(BitOP op, String destKey, String... srcKeys) {
        return selectMasterRediser().bitop(op, destKey, srcKeys);
    }

    @Override
    default  ScanResult<String> scan(int cursor) {
        return selectSlaveRediser().scan(cursor);
    }

    @Override
    default  ScanResult<String> scan(String cursor) {
        return selectSlaveRediser().scan(cursor);
    }

    @Override
    default  ScanResult<String> scan(String cursor, ScanParams params) {
        return selectSlaveRediser().scan(cursor, params);
    }

    @Override
    default  String pfmerge(String destkey, String... sourcekeys) {
        return selectMasterRediser().pfmerge(destkey, sourcekeys);
    }

    @Override
    default  long pfcount(String... keys) {
        return selectSlaveRediser().pfcount(keys);
    }
}
