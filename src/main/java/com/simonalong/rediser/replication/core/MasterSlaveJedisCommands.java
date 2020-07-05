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
public interface MasterSlaveJedisCommands extends MasterSlaveSelector, JedisCommands {

    @Override
    default String set(String key, String value) {
        return selectMasterRediser().set(key, value);
    }

    @Override
    default String set(String key, String value, String nxxx, String expx, long time) {
        return selectMasterRediser().set(key, value, nxxx, expx, time);
    }

    @Override
    default String set(String key, String value, String nxxx) {
        return selectMasterRediser().set(key, value, nxxx);
    }

    @Override
    default String get(String key) {
        return selectSlaveRediser().get(key);
    }

    @Override
    default Boolean exists(String key) {
        return selectSlaveRediser().exists(key);
    }

    @Override
    default Long persist(String key) {
        return selectMasterRediser().persist(key);
    }

    @Override
    default String type(String key) {
        return selectSlaveRediser().type(key);
    }

    @Override
    default Long expire(String key, int seconds) {
        return selectSlaveRediser().expire(key, seconds);
    }

    @Override
    default Long pexpire(String key, long milliseconds) {
        return selectSlaveRediser().pexpire(key, milliseconds);
    }

    @Override
    default Long expireAt(String key, long unixTime) {
        return selectMasterRediser().expireAt(key, unixTime);
    }

    @Override
    default Long pexpireAt(String key, long millisecondsTimestamp) {
        return selectMasterRediser().pexpireAt(key, millisecondsTimestamp);
    }

    @Override
    default Long ttl(String key) {
        return selectMasterRediser().ttl(key);
    }

    @Override
    default Long pttl(String key) {
        return selectMasterRediser().pttl(key);
    }

    @Override
    default Boolean setbit(String key, long offset, boolean value) {
        return selectMasterRediser().setbit(key, offset, value);
    }

    @Override
    default Boolean setbit(String key, long offset, String value) {
        return selectMasterRediser().setbit(key, offset, value);
    }

    @Override
    default Boolean getbit(String key, long offset) {
        return selectSlaveRediser().getbit(key, offset);
    }

    @Override
    default Long setrange(String key, long offset, String value) {
        return selectMasterRediser().setrange(key, offset, value);
    }

    @Override
    default String getrange(String key, long startOffset, long endOffset) {
        return selectSlaveRediser().getrange(key, startOffset, endOffset);
    }

    @Override
    default String getSet(String key, String value) {
        return selectMasterRediser().getSet(key, value);
    }

    @Override
    default Long setnx(String key, String value) {
        return selectMasterRediser().setnx(key, value);
    }

    @Override
    default String setex(String key, int seconds, String value) {
        return selectMasterRediser().setex(key, seconds, value);
    }

    @Override
    default String psetex(String key, long milliseconds, String value) {
        return selectMasterRediser().psetex(key, milliseconds, value);
    }

    @Override
    default Long decrBy(String key, long integer) {
        return selectMasterRediser().decrBy(key, integer);
    }

    @Override
    default Long decr(String key) {
        return selectMasterRediser().decr(key);
    }

    @Override
    default Long incrBy(String key, long integer) {
        return selectMasterRediser().incrBy(key, integer);
    }

    @Override
    default Double incrByFloat(String key, double value) {
        return selectMasterRediser().incrByFloat(key, value);
    }

    @Override
    default Long incr(String key) {
        return selectMasterRediser().incr(key);
    }

    @Override
    default Long append(String key, String value) {
        return selectMasterRediser().append(key, value);
    }

    @Override
    default String substr(String key, int start, int end) {
        return selectSlaveRediser().substr(key, start, end);
    }

    @Override
    default Long hset(String key, String field, String value) {
        return selectMasterRediser().hset(key, field, value);
    }

    @Override
    default String hget(String key, String field) {
        return selectSlaveRediser().hget(key, field);
    }

    @Override
    default Long hsetnx(String key, String field, String value) {
        return selectMasterRediser().hsetnx(key, field, value);
    }

    @Override
    default String hmset(String key, Map<String, String> hash) {
        return selectMasterRediser().hmset(key, hash);
    }

    @Override
    default List<String> hmget(String key, String... fields) {
        return selectSlaveRediser().hmget(key, fields);
    }

    @Override
    default Long hincrBy(String key, String field, long value) {
        return selectMasterRediser().hincrBy(key, field, value);
    }

    @Override
    default Double hincrByFloat(String key, String field, double value) {
        return selectMasterRediser().hincrByFloat(key, field, value);
    }

    @Override
    default Boolean hexists(String key, String field) {
        return selectSlaveRediser().hexists(key, field);
    }

    @Override
    default Long hdel(String key, String... field) {
        return selectMasterRediser().hdel(key, field);
    }

    @Override
    default Long hlen(String key) {
        return selectSlaveRediser().hlen(key);
    }

    @Override
    default Set<String> hkeys(String key) {
        return selectSlaveRediser().hkeys(key);
    }

    @Override
    default List<String> hvals(String key) {
        return selectSlaveRediser().hvals(key);
    }

    @Override
    default Map<String, String> hgetAll(String key) {
        return selectSlaveRediser().hgetAll(key);
    }

    @Override
    default Long rpush(String key, String... string) {
        return selectMasterRediser().rpush(key, string);
    }

    @Override
    default Long lpush(String key, String... string) {
        return selectMasterRediser().lpush(key, string);
    }

    @Override
    default Long llen(String key) {
        return selectSlaveRediser().llen(key);
    }

    @Override
    default List<String> lrange(String key, long start, long end) {
        return selectSlaveRediser().lrange(key, start, end);
    }

    @Override
    default String ltrim(String key, long start, long end) {
        return selectMasterRediser().ltrim(key, start, end);
    }

    @Override
    default String lindex(String key, long index) {
        return selectSlaveRediser().lindex(key, index);
    }

    @Override
    default String lset(String key, long index, String value) {
        return selectMasterRediser().lset(key, index, value);
    }

    @Override
    default Long lrem(String key, long count, String value) {
        return selectSlaveRediser().lrem(key, count, value);
    }

    @Override
    default String lpop(String key) {
        return selectSlaveRediser().lpop(key);
    }

    @Override
    default String rpop(String key) {
        return selectSlaveRediser().rpop(key);
    }

    @Override
    default Long sadd(String key, String... member) {
        return selectMasterRediser().sadd(key, member);
    }

    @Override
    default Set<String> smembers(String key) {
        return selectSlaveRediser().smembers(key);
    }

    @Override
    default Long srem(String key, String... member) {
        return selectMasterRediser().srem(key, member);
    }

    @Override
    default String spop(String key) {
        return selectMasterRediser().spop(key);
    }

    @Override
    default Set<String> spop(String key, long count) {
        return selectMasterRediser().spop(key, count);
    }

    @Override
    default Long scard(String key) {
        return selectSlaveRediser().scard(key);
    }

    @Override
    default Boolean sismember(String key, String member) {
        return selectSlaveRediser().sismember(key, member);
    }

    @Override
    default String srandmember(String key) {
        return selectSlaveRediser().srandmember(key);
    }

    @Override
    default List<String> srandmember(String key, int count) {
        return selectSlaveRediser().srandmember(key, count);
    }

    @Override
    default Long strlen(String key) {
        return selectSlaveRediser().strlen(key);
    }

    @Override
    default Long zadd(String key, double score, String member) {
        return selectMasterRediser().zadd(key, score, member);
    }

    @Override
    default Long zadd(String key, double score, String member, ZAddParams params) {
        return selectMasterRediser().zadd(key, score, member, params);
    }

    @Override
    default Long zadd(String key, Map<String, Double> scoreMembers) {
        return selectMasterRediser().zadd(key, scoreMembers);
    }

    @Override
    default Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        return selectMasterRediser().zadd(key, scoreMembers, params);
    }

    @Override
    default Set<String> zrange(String key, long start, long end) {
        return selectSlaveRediser().zrange(key, start, end);
    }

    @Override
    default Long zrem(String key, String... member) {
        return selectMasterRediser().zrem(key, member);
    }

    @Override
    default Double zincrby(String key, double score, String member) {
        return selectMasterRediser().zincrby(key, score, member);
    }

    @Override
    default Double zincrby(String key, double score, String member, ZIncrByParams params) {
        return selectMasterRediser().zincrby(key, score, member, params);
    }

    @Override
    default Long zrank(String key, String member) {
        return selectSlaveRediser().zrank(key, member);
    }

    @Override
    default Long zrevrank(String key, String member) {
        return selectSlaveRediser().zrevrank(key, member);
    }

    @Override
    default Set<String> zrevrange(String key, long start, long end) {
        return selectSlaveRediser().zrevrange(key, start, end);
    }

    @Override
    default Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return selectSlaveRediser().zrangeWithScores(key, start, end);
    }

    @Override
    default Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        return selectSlaveRediser().zrevrangeWithScores(key, start, end);
    }

    @Override
    default Long zcard(String key) {
        return selectSlaveRediser().zcard(key);
    }

    @Override
    default Double zscore(String key, String member) {
        return selectSlaveRediser().zscore(key, member);
    }

    @Override
    default List<String> sort(String key) {
        return selectMasterRediser().sort(key);
    }

    @Override
    default List<String> sort(String key, SortingParams sortingParameters) {
        return selectMasterRediser().sort(key, sortingParameters);
    }

    @Override
    default Long zcount(String key, double min, double max) {
        return selectSlaveRediser().zcount(key, min, max);
    }

    @Override
    default Long zcount(String key, String min, String max) {
        return selectSlaveRediser().zcount(key, min, max);
    }

    @Override
    default Set<String> zrangeByScore(String key, double min, double max) {
        return selectSlaveRediser().zrangeByScore(key, min, max);
    }

    @Override
    default Set<String> zrangeByScore(String key, String min, String max) {
        return selectSlaveRediser().zrangeByScore(key, min, max);
    }

    @Override
    default Set<String> zrevrangeByScore(String key, double max, double min) {
        return selectSlaveRediser().zrevrangeByScore(key, max, min);
    }

    @Override
    default Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return selectSlaveRediser().zrangeByScore(key, min, max, offset, count);
    }

    @Override
    default Set<String> zrevrangeByScore(String key, String max, String min) {
        return selectSlaveRediser().zrevrangeByScore(key, max, min);
    }

    @Override
    default Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        return selectSlaveRediser().zrangeByScore(key, min, max, offset, count);
    }

    @Override
    default Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return selectSlaveRediser().zrevrangeByScore(key, max, min, offset, count);
    }

    @Override
    default Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return selectSlaveRediser().zrangeByScoreWithScores(key, min, max);
    }

    @Override
    default Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return selectSlaveRediser().zrevrangeByScoreWithScores(key, max, min);
    }

    @Override
    default Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return selectSlaveRediser().zrangeByScoreWithScores(key, min, max, offset, count);
    }

    @Override
    default Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        return selectSlaveRediser().zrevrangeByScore(key, max, min, offset, count);
    }

    @Override
    default Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        return selectSlaveRediser().zrangeByScoreWithScores(key, min, max);
    }

    @Override
    default Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        return selectSlaveRediser().zrevrangeByScoreWithScores(key, max, min);
    }

    @Override
    default Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        return selectSlaveRediser().zrangeByScoreWithScores(key, min, max, offset, count);
    }

    @Override
    default Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        return selectSlaveRediser().zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    @Override
    default Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        return selectSlaveRediser().zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    @Override
    default Long zremrangeByRank(String key, long start, long end) {
        return selectMasterRediser().zremrangeByRank(key, start, end);
    }

    @Override
    default Long zremrangeByScore(String key, double start, double end) {
        return selectMasterRediser().zremrangeByScore(key, start, end);
    }

    @Override
    default Long zremrangeByScore(String key, String start, String end) {
        return selectMasterRediser().zremrangeByScore(key, start, end);
    }

    @Override
    default Long zlexcount(String key, String min, String max) {
        return selectSlaveRediser().zlexcount(key, min, max);
    }

    @Override
    default Set<String> zrangeByLex(String key, String min, String max) {
        return selectSlaveRediser().zrangeByLex(key, min, max);
    }

    @Override
    default Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        return selectSlaveRediser().zrangeByLex(key, min, max, offset, count);
    }

    @Override
    default Set<String> zrevrangeByLex(String key, String max, String min) {
        return selectSlaveRediser().zrevrangeByLex(key, max, min);
    }

    @Override
    default Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        return selectSlaveRediser().zrevrangeByLex(key, max, min, offset, count);
    }

    @Override
    default Long zremrangeByLex(String key, String min, String max) {
        return selectMasterRediser().zremrangeByLex(key, min, max);
    }

    @Override
    default Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        return selectMasterRediser().linsert(key, where, pivot, value);
    }

    @Override
    default Long lpushx(String key, String... string) {
        return selectMasterRediser().lpushx(key, string);
    }

    @Override
    default Long rpushx(String key, String... string) {
        return selectMasterRediser().rpushx(key, string);
    }

    @Override
    default List<String> blpop(String arg) {
        return selectMasterRediser().blpop(arg);
    }

    @Override
    default List<String> blpop(int timeout, String key) {
        return selectMasterRediser().blpop(timeout, key);
    }

    @Override
    default List<String> brpop(String arg) {
        return selectMasterRediser().brpop(arg);
    }

    @Override
    default List<String> brpop(int timeout, String key) {
        return selectMasterRediser().brpop(timeout, key);
    }

    @Override
    default Long del(String key) {
        return selectMasterRediser().del(key);
    }

    @Override
    default String echo(String string) {
        return selectSlaveRediser().echo(string);
    }

    @Override
    default Long move(String key, int dbIndex) {
        return selectMasterRediser().move(key, dbIndex);
    }

    @Override
    default Long bitcount(String key) {
        return selectSlaveRediser().bitcount(key);
    }

    @Override
    default Long bitcount(String key, long start, long end) {
        return selectSlaveRediser().bitcount(key, start, end);
    }

    @Override
    default Long bitpos(String key, boolean value) {
        return selectSlaveRediser().bitpos(key, value);
    }

    @Override
    default Long bitpos(String key, boolean value, BitPosParams params) {
        return selectSlaveRediser().bitpos(key, value, params);
    }

    @Override
    default ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
        return selectSlaveRediser().hscan(key, cursor);
    }

    @Override
    default ScanResult<String> sscan(String key, int cursor) {
        return selectSlaveRediser().sscan(key, cursor);
    }

    @Override
    default ScanResult<Tuple> zscan(String key, int cursor) {
        return selectSlaveRediser().zscan(key, cursor);
    }

    @Override
    default ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        return selectSlaveRediser().hscan(key, cursor);
    }

    @Override
    default ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
        return selectSlaveRediser().hscan(key, cursor, params);
    }

    @Override
    default ScanResult<String> sscan(String key, String cursor) {
        return selectSlaveRediser().sscan(key, cursor);
    }

    @Override
    default ScanResult<String> sscan(String key, String cursor, ScanParams params) {
        return selectSlaveRediser().sscan(key, cursor, params);
    }

    @Override
    default ScanResult<Tuple> zscan(String key, String cursor) {
        return selectSlaveRediser().zscan(key, cursor);
    }

    @Override
    default ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        return selectSlaveRediser().zscan(key, cursor, params);
    }

    @Override
    default Long pfadd(String key, String... elements) {
        return selectMasterRediser().pfadd(key, elements);
    }

    @Override
    default long pfcount(String key) {
        return selectSlaveRediser().pfcount(key);
    }

    @Override
    default Long geoadd(String key, double longitude, double latitude, String member) {
        return selectMasterRediser().geoadd(key, longitude, latitude, member);
    }

    @Override
    default Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        return selectMasterRediser().geoadd(key, memberCoordinateMap);
    }

    @Override
    default Double geodist(String key, String member1, String member2) {
        return selectSlaveRediser().geodist(key, member1, member2);
    }

    @Override
    default Double geodist(String key, String member1, String member2, GeoUnit unit) {
        return selectSlaveRediser().geodist(key, member1, member2, unit);
    }

    @Override
    default List<String> geohash(String key, String... members) {
        return selectSlaveRediser().geohash(key, members);
    }

    @Override
    default List<GeoCoordinate> geopos(String key, String... members) {
        return selectSlaveRediser().geopos(key, members);
    }

    @Override
    default List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        return selectMasterRediser().georadius(key, longitude, latitude, radius, unit);
    }

    @Override
    default List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return selectMasterRediser().georadius(key, longitude, latitude, radius, unit, param);
    }

    @Override
    default List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
        return selectMasterRediser().georadiusByMember(key, member, radius, unit);
    }

    @Override
    default List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return selectMasterRediser().georadiusByMember(key, member, radius, unit, param);
    }

    @Override
    default List<Long> bitfield(String key, String... arguments) {
        return selectMasterRediser().bitfield(key, arguments);
    }
}
