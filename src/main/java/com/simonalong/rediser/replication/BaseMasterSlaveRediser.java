package com.simonalong.rediser.replication;

import com.simonalong.rediser.replication.core.*;

/**
 * @author shizi
 * @since 2020/7/5 3:49 PM
 */
public interface BaseMasterSlaveRediser extends MasterSlaveAdvancedJedisCommands, MasterSlaveBasicCommands, MasterSlaveHashHandler, MasterSlaveJedisCommands, MasterSlaveObjectCommand, MasterSlaveObjectQuery, MasterSlaveScriptingCommands, MasterSlaveSelector {

}
