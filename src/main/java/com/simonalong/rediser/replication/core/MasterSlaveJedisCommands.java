package com.simonalong.rediser.replication.core;

import com.simonalong.rediser.replication.MasterSlaveSelector;
import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author shizi
 * @since 2020/7/5 4:32 PM
 */
public interface MasterSlaveJedisCommands extends MasterSlaveHandler, MasterSlaveSelector, JedisCommands {

    @Override
    default String set(String key, String value) {
        return doMasterCall(rediser -> rediser.set(key, value));
    }

    @Override
    default String set(String key, String value, String nxxx, String expx, long time) {
        return doMasterCall(rediser -> rediser.set(key, value, nxxx, expx, time));
    }

    @Override
    default String set(String key, String value, String nxxx) {
        return doMasterCall(rediser -> rediser.set(key, value, nxxx));
    }

    @Override
    default String get(String key) {
        return doSlaveCall(rediser -> rediser.get(key));
    }

    @Override
    default Boolean exists(String key) {
        return doSlaveCall(rediser -> rediser.exists(key));
    }

    @Override
    default Long persist(String key) {
        return doMasterCall(rediser -> rediser.persist(key));
    }

    @Override
    default String type(String key) {
        return doSlaveCall(rediser -> rediser.type(key));
    }

    @Override
    default Long expire(String key, int seconds) {
        return doSlaveCall(rediser -> rediser.expire(key, seconds));
    }

    @Override
    default Long pexpire(String key, long milliseconds) {
        return doSlaveCall(rediser -> rediser.pexpire(key, milliseconds));
    }

    @Override
    default Long expireAt(String key, long unixTime) {
        return doMasterCall(rediser -> rediser.expireAt(key, unixTime));
    }

    @Override
    default Long pexpireAt(String key, long millisecondsTimestamp) {
        return doMasterCall(rediser -> rediser.pexpireAt(key, millisecondsTimestamp));
    }

    @Override
    default Long ttl(String key) {
        return doMasterCall(rediser -> rediser.ttl(key));
    }

    @Override
    default Long pttl(String key) {
        return doMasterCall(rediser -> rediser.pttl(key));
    }

    @Override
    default Boolean setbit(String key, long offset, boolean value) {
        return doMasterCall(rediser -> rediser.setbit(key, offset, value));
    }

    @Override
    default Boolean setbit(String key, long offset, String value) {
        return doMasterCall(rediser -> rediser.setbit(key, offset, value));
    }

    @Override
    default Boolean getbit(String key, long offset) {
        return doSlaveCall(rediser -> rediser.getbit(key, offset));
    }

    @Override
    default Long setrange(String key, long offset, String value) {
        return doMasterCall(rediser -> rediser.setrange(key, offset, value));
    }

    @Override
    default String getrange(String key, long startOffset, long endOffset) {
        return doSlaveCall(rediser -> rediser.getrange(key, startOffset, endOffset));
    }

    @Override
    default String getSet(String key, String value) {
        return doMasterCall(rediser -> rediser.getSet(key, value));
    }

    @Override
    default Long setnx(String key, String value) {
        return doMasterCall(rediser -> rediser.setnx(key, value));
    }

    @Override
    default String setex(String key, int seconds, String value) {
        return doMasterCall(rediser -> rediser.setex(key, seconds, value));
    }

    @Override
    default String psetex(String key, long milliseconds, String value) {
        return doMasterCall(rediser -> rediser.psetex(key, milliseconds, value));
    }

    @Override
    default Long decrBy(String key, long integer) {
        return doMasterCall(rediser -> rediser.decrBy(key, integer));
    }

    @Override
    default Long decr(String key) {
        return doMasterCall(rediser -> rediser.decr(key));
    }

    @Override
    default Long incrBy(String key, long integer) {
        return doMasterCall(rediser -> rediser.incrBy(key, integer));
    }

    @Override
    default Double incrByFloat(String key, double value) {
        return doMasterCall(rediser -> rediser.incrByFloat(key, value));
    }

    @Override
    default Long incr(String key) {
        return doMasterCall(rediser -> rediser.incr(key));
    }

    @Override
    default Long append(String key, String value) {
        return doMasterCall(rediser -> rediser.append(key, value));
    }

    @Override
    default String substr(String key, int start, int end) {
        return doSlaveCall(rediser -> rediser.substr(key, start, end));
    }

    @Override
    default Long hset(String key, String field, String value) {
        return doMasterCall(rediser -> rediser.hset(key, field, value));
    }

    @Override
    default String hget(String key, String field) {
        return doSlaveCall(rediser -> rediser.hget(key, field));
    }

    @Override
    default Long hsetnx(String key, String field, String value) {
        return doMasterCall(rediser -> rediser.hsetnx(key, field, value));
    }

    @Override
    default String hmset(String key, Map<String, String> hash) {
        return doMasterCall(rediser -> rediser.hmset(key, hash));
    }

    @Override
    default List<String> hmget(String key, String... fields) {
        return doSlaveCall(rediser -> rediser.hmget(key, fields));
    }

    @Override
    default Long hincrBy(String key, String field, long value) {
        return doMasterCall(rediser -> rediser.hincrBy(key, field, value));
    }

    @Override
    default Double hincrByFloat(String key, String field, double value) {
        return doMasterCall(rediser -> rediser.hincrByFloat(key, field, value));
    }

    @Override
    default Boolean hexists(String key, String field) {
        return doSlaveCall(rediser -> rediser.hexists(key, field));
    }

    @Override
    default Long hdel(String key, String... field) {
        return doMasterCall(rediser -> rediser.hdel(key, field));
    }

    @Override
    default Long hlen(String key) {
        return doSlaveCall(rediser -> rediser.hlen(key));
    }

    @Override
    default Set<String> hkeys(String key) {
        return doSlaveCall(rediser -> rediser.hkeys(key));
    }

    @Override
    default List<String> hvals(String key) {
        return doSlaveCall(rediser -> rediser.hvals(key));
    }

    @Override
    default Map<String, String> hgetAll(String key) {
        return doSlaveCall(rediser -> rediser.hgetAll(key));
    }

    @Override
    default Long rpush(String key, String... string) {
        return doMasterCall(rediser -> rediser.rpush(key, string));
    }

    @Override
    default Long lpush(String key, String... string) {
        return doMasterCall(rediser -> rediser.lpush(key, string));
    }

    @Override
    default Long llen(String key) {
        return doSlaveCall(rediser -> rediser.llen(key));
    }

    @Override
    default List<String> lrange(String key, long start, long end) {
        return doSlaveCall(rediser -> rediser.lrange(key, start, end));
    }

    @Override
    default String ltrim(String key, long start, long end) {
        return doMasterCall(rediser -> rediser.ltrim(key, start, end));
    }

    @Override
    default String lindex(String key, long index) {
        return doSlaveCall(rediser -> rediser.lindex(key, index));
    }

    @Override
    default String lset(String key, long index, String value) {
        return doMasterCall(rediser -> rediser.lset(key, index, value));
    }

    @Override
    default Long lrem(String key, long count, String value) {
        return doSlaveCall(rediser -> rediser.lrem(key, count, value));
    }

    @Override
    default String lpop(String key) {
        return doSlaveCall(rediser -> rediser.lpop(key));
    }

    @Override
    default String rpop(String key) {
        return doSlaveCall(rediser -> rediser.rpop(key));
    }

    @Override
    default Long sadd(String key, String... member) {
        return doMasterCall(rediser -> rediser.sadd(key, member));
    }

    @Override
    default Set<String> smembers(String key) {
        return doSlaveCall(rediser -> rediser.smembers(key));
    }

    @Override
    default Long srem(String key, String... member) {
        return doMasterCall(rediser -> rediser.srem(key, member));
    }

    @Override
    default String spop(String key) {
        return doMasterCall(rediser -> rediser.spop(key));
    }

    @Override
    default Set<String> spop(String key, long count) {
        return doMasterCall(rediser -> rediser.spop(key, count));
    }

    @Override
    default Long scard(String key) {
        return doSlaveCall(rediser -> rediser.scard(key));
    }

    @Override
    default Boolean sismember(String key, String member) {
        return doSlaveCall(rediser -> rediser.sismember(key, member));
    }

    @Override
    default String srandmember(String key) {
        return doSlaveCall(rediser -> rediser.srandmember(key));
    }

    @Override
    default List<String> srandmember(String key, int count) {
        return doSlaveCall(rediser -> rediser.srandmember(key, count));
    }

    @Override
    default Long strlen(String key) {
        return doSlaveCall(rediser -> rediser.strlen(key));
    }

    @Override
    default Long zadd(String key, double score, String member) {
        return doMasterCall(rediser -> rediser.zadd(key, score, member));
    }

    @Override
    default Long zadd(String key, double score, String member, ZAddParams params) {
        return doMasterCall(rediser -> rediser.zadd(key, score, member, params));
    }

    @Override
    default Long zadd(String key, Map<String, Double> scoreMembers) {
        return doMasterCall(rediser -> rediser.zadd(key, scoreMembers));
    }

    @Override
    default Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        return doMasterCall(rediser -> rediser.zadd(key, scoreMembers, params));
    }

    @Override
    default Set<String> zrange(String key, long start, long end) {
        return doSlaveCall(rediser -> rediser.zrange(key, start, end));
    }

    @Override
    default Long zrem(String key, String... member) {
        return doMasterCall(rediser -> rediser.zrem(key, member));
    }

    @Override
    default Double zincrby(String key, double score, String member) {
        return doMasterCall(rediser -> rediser.zincrby(key, score, member));
    }

    @Override
    default Double zincrby(String key, double score, String member, ZIncrByParams params) {
        return doMasterCall(rediser -> rediser.zincrby(key, score, member, params));
    }

    @Override
    default Long zrank(String key, String member) {
        return doSlaveCall(rediser -> rediser.zrank(key, member));
    }

    @Override
    default Long zrevrank(String key, String member) {
        return doSlaveCall(rediser -> rediser.zrevrank(key, member));
    }

    @Override
    default Set<String> zrevrange(String key, long start, long end) {
        return doSlaveCall(rediser -> rediser.zrevrange(key, start, end));
    }

    @Override
    default Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return doSlaveCall(rediser -> rediser.zrangeWithScores(key, start, end));
    }

    @Override
    default Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        return doSlaveCall(rediser -> rediser.zrevrangeWithScores(key, start, end));
    }

    @Override
    default Long zcard(String key) {
        return doSlaveCall(rediser -> rediser.zcard(key));
    }

    @Override
    default Double zscore(String key, String member) {
        return doSlaveCall(rediser -> rediser.zscore(key, member));
    }

    @Override
    default List<String> sort(String key) {
        return doMasterCall(rediser -> rediser.sort(key));
    }

    @Override
    default List<String> sort(String key, SortingParams sortingParameters) {
        return doMasterCall(rediser -> rediser.sort(key, sortingParameters));
    }

    @Override
    default Long zcount(String key, double min, double max) {
        return doSlaveCall(rediser -> rediser.zcount(key, min, max));
    }

    @Override
    default Long zcount(String key, String min, String max) {
        return doSlaveCall(rediser -> rediser.zcount(key, min, max));
    }

    @Override
    default Set<String> zrangeByScore(String key, double min, double max) {
        return doSlaveCall(rediser -> rediser.zrangeByScore(key, min, max));
    }

    @Override
    default Set<String> zrangeByScore(String key, String min, String max) {
        return doSlaveCall(rediser -> rediser.zrangeByScore(key, min, max));
    }

    @Override
    default Set<String> zrevrangeByScore(String key, double max, double min) {
        return doSlaveCall(rediser -> rediser.zrevrangeByScore(key, max, min));
    }

    @Override
    default Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return doSlaveCall(rediser -> rediser.zrangeByScore(key, min, max, offset, count));
    }

    @Override
    default Set<String> zrevrangeByScore(String key, String max, String min) {
        return doSlaveCall(rediser -> rediser.zrevrangeByScore(key, max, min));
    }

    @Override
    default Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        return doSlaveCall(rediser -> rediser.zrangeByScore(key, min, max, offset, count));
    }

    @Override
    default Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return doSlaveCall(rediser -> rediser.zrevrangeByScore(key, max, min, offset, count));
    }

    @Override
    default Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return doSlaveCall(rediser -> rediser.zrangeByScoreWithScores(key, min, max));
    }

    @Override
    default Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return doSlaveCall(rediser -> rediser.zrevrangeByScoreWithScores(key, max, min));
    }

    @Override
    default Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return doSlaveCall(rediser -> rediser.zrangeByScoreWithScores(key, min, max, offset, count));
    }

    @Override
    default Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        return doSlaveCall(rediser -> rediser.zrevrangeByScore(key, max, min, offset, count));
    }

    @Override
    default Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        return doSlaveCall(rediser -> rediser.zrangeByScoreWithScores(key, min, max));
    }

    @Override
    default Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        return doSlaveCall(rediser -> rediser.zrevrangeByScoreWithScores(key, max, min));
    }

    @Override
    default Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        return doSlaveCall(rediser -> rediser.zrangeByScoreWithScores(key, min, max, offset, count));
    }

    @Override
    default Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        return doSlaveCall(rediser -> rediser.zrevrangeByScoreWithScores(key, max, min, offset, count));
    }

    @Override
    default Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        return doSlaveCall(rediser -> rediser.zrevrangeByScoreWithScores(key, max, min, offset, count));
    }

    @Override
    default Long zremrangeByRank(String key, long start, long end) {
        return doMasterCall(rediser -> rediser.zremrangeByRank(key, start, end));
    }

    @Override
    default Long zremrangeByScore(String key, double start, double end) {
        return doMasterCall(rediser -> rediser.zremrangeByScore(key, start, end));
    }

    @Override
    default Long zremrangeByScore(String key, String start, String end) {
        return doMasterCall(rediser -> rediser.zremrangeByScore(key, start, end));
    }

    @Override
    default Long zlexcount(String key, String min, String max) {
        return doSlaveCall(rediser -> rediser.zlexcount(key, min, max));
    }

    @Override
    default Set<String> zrangeByLex(String key, String min, String max) {
        return doSlaveCall(rediser -> rediser.zrangeByLex(key, min, max));
    }

    @Override
    default Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        return doSlaveCall(rediser -> rediser.zrangeByLex(key, min, max, offset, count));
    }

    @Override
    default Set<String> zrevrangeByLex(String key, String max, String min) {
        return doSlaveCall(rediser -> rediser.zrevrangeByLex(key, max, min));
    }

    @Override
    default Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        return doSlaveCall(rediser -> rediser.zrevrangeByLex(key, max, min, offset, count));
    }

    @Override
    default Long zremrangeByLex(String key, String min, String max) {
        return doMasterCall(rediser -> rediser.zremrangeByLex(key, min, max));
    }

    @Override
    default Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        return doMasterCall(rediser -> rediser.linsert(key, where, pivot, value));
    }

    @Override
    default Long lpushx(String key, String... string) {
        return doMasterCall(rediser -> rediser.lpushx(key, string));
    }

    @Override
    default Long rpushx(String key, String... string) {
        return doMasterCall(rediser -> rediser.rpushx(key, string));
    }

    @Override
    default List<String> blpop(String arg) {
        return doMasterCall(rediser -> rediser.blpop(arg));
    }

    @Override
    default List<String> blpop(int timeout, String key) {
        return doMasterCall(rediser -> rediser.blpop(timeout, key));
    }

    @Override
    default List<String> brpop(String arg) {
        return doMasterCall(rediser -> rediser.brpop(arg));
    }

    @Override
    default List<String> brpop(int timeout, String key) {
        return doMasterCall(rediser -> rediser.brpop(timeout, key));
    }

    @Override
    default Long del(String key) {
        return doMasterCall(rediser -> rediser.del(key));
    }

    @Override
    default String echo(String string) {
        return doSlaveCall(rediser -> rediser.echo(string));
    }

    @Override
    default Long move(String key, int dbIndex) {
        return doMasterCall(rediser -> rediser.move(key, dbIndex));
    }

    @Override
    default Long bitcount(String key) {
        return doSlaveCall(rediser -> rediser.bitcount(key));
    }

    @Override
    default Long bitcount(String key, long start, long end) {
        return doSlaveCall(rediser -> rediser.bitcount(key, start, end));
    }

    @Override
    default Long bitpos(String key, boolean value) {
        return doSlaveCall(rediser -> rediser.bitpos(key, value));
    }

    @Override
    default Long bitpos(String key, boolean value, BitPosParams params) {
        return doSlaveCall(rediser -> rediser.bitpos(key, value, params));
    }

    @Override
    default ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
        return doSlaveCall(rediser -> rediser.hscan(key, cursor));
    }

    @Override
    default ScanResult<String> sscan(String key, int cursor) {
        return doSlaveCall(rediser -> rediser.sscan(key, cursor));
    }

    @Override
    default ScanResult<Tuple> zscan(String key, int cursor) {
        return doSlaveCall(rediser -> rediser.zscan(key, cursor));
    }

    @Override
    default ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        return doSlaveCall(rediser -> rediser.hscan(key, cursor));
    }

    @Override
    default ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
        return doSlaveCall(rediser -> rediser.hscan(key, cursor, params));
    }

    @Override
    default ScanResult<String> sscan(String key, String cursor) {
        return doSlaveCall(rediser -> rediser.sscan(key, cursor));
    }

    @Override
    default ScanResult<String> sscan(String key, String cursor, ScanParams params) {
        return doSlaveCall(rediser -> rediser.sscan(key, cursor, params));
    }

    @Override
    default ScanResult<Tuple> zscan(String key, String cursor) {
        return doSlaveCall(rediser -> rediser.zscan(key, cursor));
    }

    @Override
    default ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        return doSlaveCall(rediser -> rediser.zscan(key, cursor, params));
    }

    @Override
    default Long pfadd(String key, String... elements) {
        return doMasterCall(rediser -> rediser.pfadd(key, elements));
    }

    @Override
    default long pfcount(String key) {
        return doSlaveCall(rediser -> rediser.pfcount(key));
    }

    @Override
    default Long geoadd(String key, double longitude, double latitude, String member) {
        return doMasterCall(rediser -> rediser.geoadd(key, longitude, latitude, member));
    }

    @Override
    default Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        return doMasterCall(rediser -> rediser.geoadd(key, memberCoordinateMap));
    }

    @Override
    default Double geodist(String key, String member1, String member2) {
        return doSlaveCall(rediser -> rediser.geodist(key, member1, member2));
    }

    @Override
    default Double geodist(String key, String member1, String member2, GeoUnit unit) {
        return doSlaveCall(rediser -> rediser.geodist(key, member1, member2, unit));
    }

    @Override
    default List<String> geohash(String key, String... members) {
        return doSlaveCall(rediser -> rediser.geohash(key, members));
    }

    @Override
    default List<GeoCoordinate> geopos(String key, String... members) {
        return doSlaveCall(rediser -> rediser.geopos(key, members));
    }

    @Override
    default List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        return doMasterCall(rediser -> rediser.georadius(key, longitude, latitude, radius, unit));
    }

    @Override
    default List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return doMasterCall(rediser -> rediser.georadius(key, longitude, latitude, radius, unit, param));
    }

    @Override
    default List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
        return doMasterCall(rediser -> rediser.georadiusByMember(key, member, radius, unit));
    }

    @Override
    default List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return doMasterCall(rediser -> rediser.georadiusByMember(key, member, radius, unit, param));
    }

    @Override
    default List<Long> bitfield(String key, String... arguments) {
        return doMasterCall(rediser -> rediser.bitfield(key, arguments));
    }
}
