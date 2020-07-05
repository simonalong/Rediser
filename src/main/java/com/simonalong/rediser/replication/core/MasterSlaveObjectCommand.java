package com.simonalong.rediser.replication.core;

import com.simonalong.rediser.core.ObjectCommand;
import com.simonalong.rediser.replication.MasterSlaveSelector;

/**
 * @author shizi
 * @since 2020/7/5 6:02 PM
 */
public interface MasterSlaveObjectCommand extends MasterSlaveSelector, ObjectCommand {

    @Override
    default String setObject(String key, Object value) {
        return selectMasterRediser().setObject(key, value);
    }

    @Override
    default String setObject(String key, Object value, String nxxx) {
        return selectMasterRediser().setObject(key, value, nxxx);
    }

    @Override
    default String setObject(String key, Object value, String nxxx, String expx, long time) {
        return selectMasterRediser().setObject(key, value, nxxx, expx, time);
    }

    @Override
    default Integer setNxObject(String key, Object value) {
        return selectMasterRediser().setNxObject(key, value);
    }

    @Override
    default Integer setXxObject(String key, Object value) {
        return selectMasterRediser().setXxObject(key, value);
    }

    @Override
    default Integer set(Enum enumKey, String value, Object... params) {
        return selectMasterRediser().set(enumKey, value, params);
    }

    @Override
    default String set(Enum enumKey, String value, String nxxx, Object... params) {
        return selectMasterRediser().set(enumKey, value, nxxx, params);
    }

    @Override
    default Integer setNx(Enum enumKey, String value, Object... params) {
        return selectMasterRediser().setNx(enumKey, value, params);
    }

    @Override
    default Integer setXx(Enum enumKey, String value, Object... params) {
        return selectMasterRediser().setXx(enumKey, value, params);
    }

    @Override
    default Integer setObject(Enum enumKey, Object value, Object... params) {
        return selectMasterRediser().setObject(enumKey, value, params);
    }

    @Override
    default String setObject(Enum enumKey, Object value, String nxxx, Object... params) {
        return selectMasterRediser().setObject(enumKey, value, nxxx, params);
    }

    @Override
    default Integer setNxObject(Enum enumKey, Object value, Object... params) {
        return selectMasterRediser().setNxObject(enumKey, value, params);
    }

    @Override
    default Integer setXxObject(Enum enumKey, Object value, Object... params) {
        return selectMasterRediser().setXxObject(enumKey, value, params);
    }
}
