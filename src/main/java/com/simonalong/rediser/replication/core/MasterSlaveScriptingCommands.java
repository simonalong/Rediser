package com.simonalong.rediser.replication.core;

import com.simonalong.rediser.replication.MasterSlaveSelector;
import redis.clients.jedis.ScriptingCommands;

import java.util.List;

/**
 * @author shizi
 * @since 2020/7/5 5:42 PM
 */
public interface MasterSlaveScriptingCommands extends MasterSlaveHandler, MasterSlaveSelector, ScriptingCommands {

    @Override
    default  Object eval(String script, int keyCount, String... params) {
        return doSlaveCall(rediser -> rediser.eval(script, keyCount, params));
    }

    @Override
    default  Object eval(String script, List<String> keys, List<String> args) {
        return doSlaveCall(rediser -> rediser.eval(script, keys, args));
    }

    @Override
    default  Object eval(String script) {
        return doSlaveCall(rediser -> rediser.eval(script));
    }

    @Override
    default  Object evalsha(String script) {
        return doSlaveCall(rediser -> rediser.evalsha(script));
    }

    @Override
    default  Object evalsha(String sha1, List<String> keys, List<String> args) {
        return doSlaveCall(rediser -> rediser.evalsha(sha1, keys, args));
    }

    @Override
    default  Object evalsha(String sha1, int keyCount, String... params) {
        return doSlaveCall(rediser -> rediser.evalsha(sha1, keyCount, params));
    }

    @Override
    default  Boolean scriptExists(String sha1) {
        return doSlaveCall(rediser -> rediser.scriptExists(sha1));
    }

    @Override
    default  List<Boolean> scriptExists(String... sha1) {
        return doSlaveCall(rediser -> rediser.scriptExists(sha1));
    }

    @Override
    default  String scriptLoad(String script) {
        return doSlaveCall(rediser -> rediser.scriptLoad(script));
    }
}
