package com.simonalong.rediser.replication;

import com.simonalong.rediser.Rediser;
import com.simonalong.rediser.exception.RediserException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author shizi
 * @since 2020/7/5 3:27 PM
 */
@Slf4j
public class MasterSlaveRediser implements BaseMasterSlaveRediser {

    private static final String MS_LOG_PRE = "[master-slave]";

    private static final MasterSlaveRediser INSTANCE = new MasterSlaveRediser();
    /**
     * 主库
     */
    private volatile Rediser masterRedis;
    /**
     * 从库列表
     */
    private Map<String, Rediser> slaveRediserMap = new ConcurrentHashMap<>(8);
    /**
     * 临时从库，用于在从库都不可用情况下的添加的主库
     */
    private Map<String, Rediser> slaveRediserTemMap = new ConcurrentHashMap<>(4);
    /**
     * 生病的暂时不可用的库列表
     */
    private List<Rediser> rediserSickList;
    private AtomicInteger slaveIndex = new AtomicInteger(0);
    private List<String> slaveKeys = new ArrayList<>();
    /**
     * 库断开后重连任务
     */
    private Executor restoreTask = new ThreadPoolExecutor(0, 1, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1), r -> {
        Thread thread = new Thread(r, "Rediser-Restore-Thread");
        thread.setDaemon(true);
        return thread;
    }, new ThreadPoolExecutor.DiscardPolicy());

    private MasterSlaveRediser() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (null != masterRedis) {
                masterRedis.close();
            }

            if (null != slaveRediserMap) {
                slaveRediserMap.forEach((key, value) -> value.close());
            }
        }));
    }

    public static MasterSlaveRediser getInstance() {
        return INSTANCE;
    }

    public Rediser getMasterNode() {
        return masterRedis;
    }

    public List<Rediser> getSlaveNodeList() {
        return new ArrayList<>(slaveRediserMap.values());
    }

    public void setMasterNode(String host, int port) {
        Rediser masterRediser = Rediser.getInstance();
        masterRediser.connect(host, port);
        masterRedis = masterRediser;
    }

    public void setMasterNode(Rediser rediser) {
        masterRedis = rediser;
    }

    public void addSlaveNode(String host, int port) {
        Rediser nodeRediser = Rediser.getInstance();
        nodeRediser.connect(host, port);
        slaveRediserMap.put(nodeRediser.getAlias(), nodeRediser);
        slaveKeys = new ArrayList<>(slaveRediserMap.keySet());
    }

    public void addSlaveNode(Rediser rediser) {
        slaveRediserMap.put(rediser.getAlias(), rediser);
        slaveKeys = new ArrayList<>(slaveRediserMap.keySet());
    }

    public void start() {
        masterRedis.start();
        slaveRediserMap.forEach((k, v) -> v.start());
    }

    /**
     * 获取主库
     *
     * @return 主库实例
     */
    @Override
    public Rediser selectMasterRediser() {
        if (null != masterRedis) {
            return masterRedis;
        }

        throw new RediserException("主库没有设置或者不可用");
    }

    @Override
    public Rediser selectSlaveRediser() {
        Integer index = getNextIndex();
        if (null != index) {
            String dbAlias = slaveKeys.get(index);
            Rediser innerActiveRediser;
            if (slaveRediserMap.containsKey(dbAlias)) {
                innerActiveRediser = slaveRediserMap.get(dbAlias);
            } else if (slaveRediserTemMap.containsKey(dbAlias)) {
                innerActiveRediser = slaveRediserTemMap.get(dbAlias);
            } else {
                throw new RediserException("从库获取失败");
            }
            return innerActiveRediser;
        } else {
            // 从库都不可用，则采用主库，将主库添加到临时从库中
            Rediser rediser = selectMasterRediser();
            if (null != rediser) {
                log.warn(MS_LOG_PRE + "从库都不可用，走主库{}", rediser.getAlias());
                addSlaveDbTem(rediser);
                return selectSlaveRediser();
            }
            throw new RediserException("没有可用的库");
        }
    }

    @Override
    public void deActiveMaster(String alias) {

    }

    @Override
    public void deActiveSlave(String alias) {

    }

    private void addSlaveDbTem(Rediser innerActiveRediser) {
        if (null != innerActiveRediser) {
            slaveRediserTemMap.put(innerActiveRediser.getAlias(), innerActiveRediser);
            slaveKeys.add(innerActiveRediser.getAlias());
            startRestore();
        }
    }

    /**
     * 启动恢复程序
     * <p>5分钟尝试一次
     */
    private void startRestore() {
        restoreTask.execute(() -> {
            while (haveUnActiveDb()) {
                try {
                    Thread.sleep(5 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                doRestore(true, masterDbMap.values().stream().filter(e -> !e.getActiveFlag()).collect(Collectors.toList()));
                doRestore(false, slaveDbMap.values().stream().filter(e -> !e.getActiveFlag()).collect(Collectors.toList()));
            }
        });
    }

    private Boolean haveUnActiveDb() {
        return masterDbMap.values().stream().anyMatch(e -> !e.getActiveFlag()) || slaveDbMap.values().stream().anyMatch(e -> !e.getActiveFlag());
    }

    private Integer getNextIndex() {
        int keySize = slaveRediserMap.size();
        if (0 == keySize) {
            return null;
        }
        return slaveIndex.getAndAccumulate(1, (pre, incrementNum) -> {
            int index = pre + incrementNum;
            if (index >= keySize) {
                return 0;
            }
            return index;
        });
    }

    @Data
    static class InnerActiveRediser {

        /**
         * 激活标示
         */
        private Boolean activeFlag = true;
        private String name;
        private Rediser rediser;

        InnerActiveRediser(Rediser rediser, String name) {
            this.rediser = rediser;
            this.name = name;
        }
    }
}
