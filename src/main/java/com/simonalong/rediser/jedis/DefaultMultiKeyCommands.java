package com.simonalong.rediser.jedis;

import redis.clients.jedis.*;

import java.util.List;
import java.util.Set;

/**
 * @author shizi
 * @since 2020/3/15 上午3:05
 */
public interface DefaultMultiKeyCommands extends JedisGetter, MultiKeyCommands {

    @Override
    default Long del(String... keys) {
        return getJedis().del(keys);
    }

    @Override
    default Long exists(String... keys) {
        return getJedis().exists(keys);
    }

    @Override
    default List<String> blpop(int timeout, String... keys) {
        return getJedis().blpop(timeout, keys);
    }

    @Override
    default List<String> brpop(int timeout, String... keys) {
        return getJedis().brpop(timeout, keys);
    }

    @Override
    default List<String> blpop(String... args) {
        return getJedis().blpop(args);
    }

    @Override
    default List<String> brpop(String... args) {
        return getJedis().brpop(args);
    }

    @Override
    default Set<String> keys(String pattern) {
        return getJedis().keys(pattern);
    }

    @Override
    default List<String> mget(String... keys) {
        return getJedis().mget(keys);
    }

    @Override
    default String mset(String... keysvalues) {
        return getJedis().mset(keysvalues);
    }

    @Override
    default Long msetnx(String... keysvalues) {
        return getJedis().msetnx(keysvalues);
    }

    @Override
    default String rename(String oldkey, String newkey) {
        return getJedis().rename(oldkey, newkey);
    }

    @Override
    default Long renamenx(String oldkey, String newkey) {
        return getJedis().renamenx(oldkey, newkey);
    }

    @Override
    default String rpoplpush(String srckey, String dstkey) {
        return getJedis().rpoplpush(srckey, dstkey);
    }

    @Override
    default Set<String> sdiff(String... keys) {
        return getJedis().sdiff(keys);
    }

    @Override
    default Long sdiffstore(String dstkey, String... keys) {
        return getJedis().sdiffstore(dstkey, keys);
    }

    @Override
    default Set<String> sinter(String... keys) {
        return getJedis().sinter(keys);
    }

    @Override
    default Long sinterstore(String dstkey, String... keys) {
        return getJedis().sinterstore(dstkey, keys);
    }

    @Override
    default Long smove(String srckey, String dstkey, String member) {
        return getJedis().smove(srckey, dstkey, member);
    }

    @Override
    default Long sort(String key, SortingParams sortingParameters, String dstkey) {
        return getJedis().sort(key, sortingParameters, dstkey);
    }

    @Override
    default Long sort(String key, String dstkey) {
        return getJedis().sort(key, dstkey);
    }

    @Override
    default Set<String> sunion(String... keys) {
        return getJedis().sunion(keys);
    }

    @Override
    default Long sunionstore(String dstkey, String... keys) {
        return getJedis().sunionstore(dstkey, keys);
    }

    @Override
    default String watch(String... keys) {
        return getJedis().watch(keys);
    }

    @Override
    default String unwatch() {
        return getJedis().unwatch();
    }

    @Override
    default Long zinterstore(String dstkey, String... sets) {
        return getJedis().zinterstore(dstkey, sets);
    }

    @Override
    default Long zinterstore(String dstkey, ZParams params, String... sets) {
        return getJedis().zinterstore(dstkey, params, sets);
    }

    @Override
    default Long zunionstore(String dstkey, String... sets) {
        return getJedis().zunionstore(dstkey, sets);
    }

    @Override
    default Long zunionstore(String dstkey, ZParams params, String... sets) {
        return getJedis().zunionstore(dstkey, params, sets);
    }

    @Override
    default String brpoplpush(String source, String destination, int timeout) {
        return getJedis().brpoplpush(source, destination, timeout);
    }

    @Override
    default Long publish(String channel, String message) {
        return getJedis().publish(channel, message);
    }

    @Override
    default void subscribe(JedisPubSub jedisPubSub, String... channels) {
        getJedis().subscribe(jedisPubSub, channels);
    }

    @Override
    default void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
        getJedis().psubscribe(jedisPubSub, patterns);
    }

    @Override
    default String randomKey() {
        return getJedis().randomKey();
    }

    @Override
    default Long bitop(BitOP op, String destKey, String... srcKeys) {
        return getJedis().bitop(op, destKey, srcKeys);
    }

    @Override
    default ScanResult<String> scan(int cursor) {
        return getJedis().scan(cursor);
    }

    @Override
    default ScanResult<String> scan(String cursor) {
        return getJedis().scan(cursor);
    }

    @Override
    default ScanResult<String> scan(String cursor, ScanParams params) {
        return getJedis().scan(cursor, params);
    }

    @Override
    default String pfmerge(String destkey, String... sourcekeys) {
        return getJedis().pfmerge(destkey, sourcekeys);
    }

    @Override
    default long pfcount(String... keys) {
        return getJedis().pfcount(keys);
    }
}
