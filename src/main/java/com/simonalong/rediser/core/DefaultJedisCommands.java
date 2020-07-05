package com.simonalong.rediser.core;

import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author shizi
 * @since 2020/3/15 上午1:35
 */
public interface DefaultJedisCommands extends JedisGetter, JedisCommands {

    @Override
    default String set(String key, String value) {
        return getJedis().set(key, value);
    }

    @Override
    default String set(String key, String value, String nxxx, String expx, long time) {
        return getJedis().set(key, value, nxxx, expx, time);
    }

    @Override
    default String set(String key, String value, String nxxx) {
        return getJedis().set(key, value, nxxx);
    }

    @Override
    default String get(String key) {
        return getJedis().get(key);
    }

    @Override
    default Boolean exists(String key) {
        return getJedis().exists(key);
    }

    @Override
    default Long persist(String key) {
        return getJedis().persist(key);
    }

    @Override
    default String type(String key) {
        return getJedis().type(key);
    }

    @Override
    default Long expire(String key, int seconds) {
        return getJedis().expire(key, seconds);
    }

    @Override
    default Long pexpire(String key, long milliseconds) {
        return getJedis().pexpire(key, milliseconds);
    }

    @Override
    default Long expireAt(String key, long unixTime) {
        return getJedis().pexpire(key, unixTime);
    }

    @Override
    default Long pexpireAt(String key, long millisecondsTimestamp) {
        return getJedis().pexpireAt(key, millisecondsTimestamp);
    }

    @Override
    default Long ttl(String key) {
        return getJedis().ttl(key);
    }

    @Override
    default Long pttl(final String key) {
        return getJedis().pttl(key);
    }

    @Override
    default Boolean setbit(String key, long offset, boolean value) {
        return getJedis().setbit(key, offset, value);
    }

    @Override
    default Boolean setbit(String key, long offset, String value) {
        return getJedis().setbit(key, offset, value);
    }

    @Override
    default Boolean getbit(String key, long offset) {
        return getJedis().getbit(key, offset);
    }

    @Override
    default Long setrange(String key, long offset, String value) {
        return getJedis().setrange(key, offset, value);
    }

    @Override
    default String getrange(String key, long startOffset, long endOffset) {
        return getJedis().getrange(key, startOffset, endOffset);
    }

    @Override
    default String getSet(String key, String value) {
        return getJedis().getSet(key, value);
    }

    @Override
    default Long setnx(String key, String value) {
        return getJedis().setnx(key, value);
    }

    @Override
    default String setex(String key, int seconds, String value) {
        return getJedis().setex(key, seconds, value);
    }

    @Override
    default String psetex(final String key, final long milliseconds, final String value) {
        return getJedis().psetex(key, milliseconds, value);
    }

    @Override
    default Long decrBy(String key, long integer) {
        return getJedis().decrBy(key, integer);
    }

    @Override
    default Long decr(String key) {
        return getJedis().decr(key);
    }

    @Override
    default Long incrBy(String key, long integer) {
        return getJedis().incrBy(key, integer);
    }

    @Override
    default Double incrByFloat(String key, double value) {
        return getJedis().incrByFloat(key, value);
    }

    @Override
    default Long incr(String key) {
        return getJedis().incr(key);
    }

    @Override
    default Long append(String key, String value) {
        return getJedis().append(key, value);
    }

    @Override
    default String substr(String key, int start, int end) {
        return getJedis().substr(key, start, end);
    }

    @Override
    default Long hset(String key, String field, String value) {
        return getJedis().hset(key, field, value);
    }

    @Override
    default String hget(String key, String field) {
        return getJedis().hget(key, field);
    }

    @Override
    default Long hsetnx(String key, String field, String value) {
        return getJedis().hsetnx(key, field, value);
    }

    @Override
    default String hmset(String key, Map<String, String> hash) {
        return getJedis().hmset(key, hash);
    }

    @Override
    default List<String> hmget(String key, String... fields) {
        return getJedis().hmget(key, fields);
    }

    @Override
    default Long hincrBy(String key, String field, long value) {
        return getJedis().hincrBy(key, field, value);
    }

    @Override
    default Double hincrByFloat(final String key, final String field, final double value) {
        return getJedis().hincrByFloat(key, field, value);
    }

    @Override
    default Boolean hexists(String key, String field) {
        return getJedis().hexists(key, field);
    }

    @Override
    default Long hdel(String key, String... field) {
        return getJedis().hdel(key, field);
    }

    @Override
    default Long hlen(String key) {
        return getJedis().hlen(key);
    }

    @Override
    default Set<String> hkeys(String key) {
        return getJedis().hkeys(key);
    }

    @Override
    default List<String> hvals(String key) {
        return getJedis().hvals(key);
    }

    @Override
    default Map<String, String> hgetAll(String key) {
        return getJedis().hgetAll(key);
    }

    @Override
    default Long rpush(String key, String... string) {
        return getJedis().rpush(key, string);
    }

    @Override
    default Long lpush(String key, String... string) {
        return getJedis().lpush(key, string);
    }

    @Override
    default Long llen(String key) {
        return getJedis().llen(key);
    }

    @Override
    default List<String> lrange(String key, long start, long end) {
        return getJedis().lrange(key, start, end);
    }

    @Override
    default String ltrim(String key, long start, long end) {
        return getJedis().ltrim(key, start, end);
    }

    @Override
    default String lindex(String key, long index) {
        return getJedis().lindex(key, index);
    }

    @Override
    default String lset(String key, long index, String value) {
        return getJedis().lset(key, index, value);
    }

    @Override
    default Long lrem(String key, long count, String value) {
        return getJedis().lrem(key, count, value);
    }

    @Override
    default String lpop(String key) {
        return getJedis().lpop(key);
    }

    @Override
    default String rpop(String key) {
        return getJedis().rpop(key);
    }

    @Override
    default Long sadd(String key, String... member) {
        return getJedis().sadd(key, member);
    }

    @Override
    default Set<String> smembers(String key) {
        return getJedis().smembers(key);
    }

    @Override
    default Long srem(String key, String... member) {
        return getJedis().srem(key, member);
    }

    @Override
    default String spop(String key) {
        return getJedis().spop(key);
    }

    @Override
    default Set<String> spop(String key, long count) {
        return getJedis().spop(key, count);
    }

    @Override
    default Long scard(String key) {
        return getJedis().scard(key);
    }

    @Override
    default Boolean sismember(String key, String member) {
        return getJedis().sismember(key, member);
    }

    @Override
    default String srandmember(String key) {
        return getJedis().srandmember(key);
    }

    @Override
    default List<String> srandmember(String key, int count) {
        return getJedis().srandmember(key, count);
    }

    @Override
    default Long strlen(String key) {
        return getJedis().strlen(key);
    }

    @Override
    default Long zadd(String key, double score, String member) {
        return getJedis().zadd(key, score, member);
    }

    @Override
    default Long zadd(String key, double score, String member, ZAddParams params) {
        return getJedis().zadd(key, score, member, params);
    }

    @Override
    default Long zadd(String key, Map<String, Double> scoreMembers) {
        return getJedis().zadd(key, scoreMembers);
    }

    @Override
    default Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        return getJedis().zadd(key, scoreMembers, params);
    }

    @Override
    default Set<String> zrange(String key, long start, long end) {
        return getJedis().zrange(key, start, end);
    }

    @Override
    default Long zrem(String key, String... member) {
        return getJedis().zrem(key, member);
    }

    @Override
    default Double zincrby(String key, double score, String member) {
        return getJedis().zincrby(key, score, member);
    }

    @Override
    default Double zincrby(String key, double score, String member, ZIncrByParams params) {
        return getJedis().zincrby(key, score, member, params);
    }

    @Override
    default Long zrank(String key, String member) {
        return getJedis().zrank(key, member);
    }

    @Override
    default Long zrevrank(String key, String member) {
        return getJedis().zrevrank(key, member);
    }

    @Override
    default Set<String> zrevrange(String key, long start, long end) {
        return getJedis().zrevrange(key, start, end);
    }

    @Override
    default Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return getJedis().zrangeWithScores(key, start, end);
    }

    @Override
    default Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        return getJedis().zrevrangeWithScores(key, start, end);
    }

    @Override
    default Long zcard(String key) {
        return getJedis().zcard(key);
    }

    @Override
    default Double zscore(String key, String member) {
        return getJedis().zscore(key, member);
    }

    @Override
    default List<String> sort(String key) {
        return getJedis().sort(key);
    }

    @Override
    default List<String> sort(String key, SortingParams sortingParameters) {
        return getJedis().sort(key, sortingParameters);
    }

    @Override
    default Long zcount(String key, double min, double max) {
        return getJedis().zcount(key, min, max);
    }

    @Override
    default Long zcount(String key, String min, String max) {
        return getJedis().zcount(key, min, max);
    }

    @Override
    default Set<String> zrangeByScore(String key, double min, double max) {
        return getJedis().zrangeByScore(key, min, max);
    }

    @Override
    default Set<String> zrangeByScore(String key, String min, String max) {
        return getJedis().zrangeByScore(key, min, max);
    }

    @Override
    default Set<String> zrevrangeByScore(String key, double max, double min) {
        return getJedis().zrangeByScore(key, max, min);
    }

    @Override
    default Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return getJedis().zrangeByScore(key, min, max, offset, count);
    }

    @Override
    default Set<String> zrevrangeByScore(String key, String max, String min) {
        return getJedis().zrevrangeByScore(key, max, min);
    }

    @Override
    default Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        return getJedis().zrangeByScore(key, min, max, offset, count);
    }

    @Override
    default Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return getJedis().zrevrangeByScore(key, max, min, offset, count);
    }

    @Override
    default Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return getJedis().zrangeByScoreWithScores(key, min, max);
    }

    @Override
    default Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return getJedis().zrevrangeByScoreWithScores(key, max, min);
    }

    @Override
    default Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return getJedis().zrangeByScoreWithScores(key, min, max, offset, count);
    }

    @Override
    default Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        return getJedis().zrevrangeByScore(key, max, min, offset, count);
    }

    @Override
    default Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        return getJedis().zrangeByScoreWithScores(key, min, max);
    }

    @Override
    default Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        return getJedis().zrevrangeByScoreWithScores(key, max, min);
    }

    @Override
    default Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        return getJedis().zrangeByScoreWithScores(key, min, max, offset, count);
    }

    @Override
    default Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        return getJedis().zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    @Override
    default Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        return getJedis().zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    @Override
    default Long zremrangeByRank(String key, long start, long end) {
        return getJedis().zremrangeByRank(key, start, end);
    }

    @Override
    default Long zremrangeByScore(String key, double start, double end) {
        return getJedis().zremrangeByScore(key, start, end);
    }

    @Override
    default Long zremrangeByScore(String key, String start, String end) {
        return getJedis().zremrangeByScore(key, start, end);
    }

    @Override
    default Long zlexcount(final String key, final String min, final String max) {
        return getJedis().zlexcount(key, min, max);
    }

    @Override
    default Set<String> zrangeByLex(final String key, final String min, final String max) {
        return getJedis().zrangeByLex(key, min, max);
    }

    @Override
    default Set<String> zrangeByLex(final String key, final String min, final String max, final int offset, final int count) {
        return getJedis().zrangeByLex(key, min, max, offset, count);
    }

    @Override
    default Set<String> zrevrangeByLex(final String key, final String max, final String min) {
        return getJedis().zrevrangeByLex(key, max, min);
    }

    @Override
    default Set<String> zrevrangeByLex(final String key, final String max, final String min, final int offset, final int count) {
        return getJedis().zrevrangeByLex(key, max, min, offset, count);
    }

    @Override
    default Long zremrangeByLex(final String key, final String min, final String max) {
        return getJedis().zremrangeByLex(key, min, max);
    }

    @Override
    default Long linsert(String key, Client.LIST_POSITION where, String pivot, String value) {
        return getJedis().linsert(key, where, pivot, value);
    }

    @Override
    default Long lpushx(String key, String... string) {
        return getJedis().lpushx(key, string);
    }

    @Override
    default Long rpushx(String key, String... string) {
        return getJedis().rpushx(key, string);
    }

    @Override
    default List<String> blpop(int timeout, String key) {
        return getJedis().blpop(timeout, key);
    }

    @Override
    default List<String> brpop(int timeout, String key) {
        return getJedis().brpop(timeout, key);
    }

    @Override
    default Long del(String key) {
        return getJedis().del(key);
    }

    @Override
    default String echo(String string) {
        return getJedis().echo(string);
    }

    @Override
    default Long move(String key, int dbIndex) {
        return getJedis().move(key, dbIndex);
    }

    @Override
    default Long bitcount(final String key) {
        return getJedis().bitcount(key);
    }

    @Override
    default Long bitcount(final String key, long start, long end) {
        return getJedis().bitcount(key, start, end);
    }

    @Override
    default Long bitpos(final String key, final boolean value) {
        return getJedis().bitpos(key, value);
    }

    @Override
    default Long bitpos(final String key, final boolean value, final BitPosParams params) {
        return getJedis().bitpos(key, value, params);
    }

    @Override
    default ScanResult<Map.Entry<String, String>> hscan(final String key, final String cursor) {
        return getJedis().hscan(key, cursor);
    }

    @Override
    default ScanResult<Map.Entry<String, String>> hscan(final String key, final String cursor, final ScanParams params) {
        return getJedis().hscan(key, cursor, params);
    }

    @Override
    default ScanResult<String> sscan(final String key, final String cursor) {
        return getJedis().sscan(key, cursor);
    }

    @Override
    default ScanResult<String> sscan(final String key, final String cursor, final ScanParams params) {
        return getJedis().sscan(key, cursor, params);
    }

    @Override
    default ScanResult<Tuple> zscan(final String key, final String cursor) {
        return getJedis().zscan(key, cursor);
    }

    @Override
    default ScanResult<Tuple> zscan(final String key, final String cursor, final ScanParams params) {
        return getJedis().zscan(key, cursor, params);
    }

    @Override
    default Long pfadd(final String key, final String... elements) {
        return getJedis().pfadd(key, elements);
    }

    @Override
    default long pfcount(final String key) {
        return getJedis().pfcount(key);
    }

    // Geo Commands
    @Override
    default Long geoadd(String key, double longitude, double latitude, String member) {
        return getJedis().geoadd(key, longitude, latitude, member);
    }

    @Override
    default Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        return getJedis().geoadd(key, memberCoordinateMap);
    }

    @Override
    default Double geodist(String key, String member1, String member2) {
        return getJedis().geodist(key, member1, member2);
    }

    @Override
    default Double geodist(String key, String member1, String member2, GeoUnit unit) {
        return getJedis().geodist(key, member1, member2, unit);
    }

    @Override
    default List<String> geohash(String key, String... members) {
        return getJedis().geohash(key, members);
    }

    @Override
    default List<GeoCoordinate> geopos(String key, String... members) {
        return getJedis().geopos(key, members);
    }

    @Override
    default List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        return getJedis().georadius(key, longitude, latitude, radius, unit);
    }

    @Override
    default List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return getJedis().georadius(key, longitude, latitude, radius, unit, param);
    }

    @Override
    default List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {return getJedis().georadiusByMember(key, member, radius, unit);}

    @Override
    default List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return getJedis().georadiusByMember(key, member, radius, unit, param);
    }

    /**
     * Executes BITFIELD Redis command
     *
     * @param key
     * @param arguments
     */
    @Override
    default List<Long> bitfield(String key, String... arguments) {return getJedis().bitfield(key, arguments);}

    @Override
    default List<String> blpop(String arg) {
        return getJedis().blpop(arg);
    }

    @Override
    default List<String> brpop(String arg) {
        return getJedis().brpop(arg);
    }

    @Override
    default ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
        return getJedis().hscan(key, cursor);
    }

    @Override
    default ScanResult<String> sscan(String key, int cursor) {
        return getJedis().sscan(key, cursor);
    }

    @Override
    default ScanResult<Tuple> zscan(String key, int cursor) {
        return getJedis().zscan(key, cursor);
    }
}
