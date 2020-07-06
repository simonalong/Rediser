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

import static com.simonalong.rediser.constant.RediserConstant.MS_LOG_PRE;

/**
 * @author shizi
 * @since 2020/7/5 3:27 PM
 */
@Slf4j
public class MasterSlaveRediser implements BaseMasterSlaveRediser {

    private static final MasterSlaveRediser INSTANCE = new MasterSlaveRediser();
    /**
     * 主库
     */
    private volatile InnerActiveRediser masterRedis;
    /**
     * 从库列表
     */
    private Map<String, InnerActiveRediser> slaveRediserMap = new ConcurrentHashMap<>(8);
    private AtomicInteger slaveIndex = new AtomicInteger(0);
    private volatile List<String> slaveKeys = new ArrayList<>();
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
        return masterRedis.getRediser();
    }

    public List<Rediser> getSlaveNodeList() {
        return slaveRediserMap.values().stream().map(InnerActiveRediser::getRediser).collect(Collectors.toList());
    }

    public void setMasterNode(String host, int port) {
        Rediser masterRediser = new Rediser();
        masterRediser.connect(host, port);
        masterRedis = new InnerActiveRediser(masterRediser, true);
    }

    public void setMasterNode(Rediser rediser) {
        masterRedis = new InnerActiveRediser(rediser, true);
    }

    public void addSlaveNode(String host, int port) {
        Rediser nodeRediser = new Rediser();
        nodeRediser.connect(host, port);
        slaveRediserMap.put(nodeRediser.getAlias(), new InnerActiveRediser(nodeRediser, false));
        slaveKeys = new ArrayList<>(slaveRediserMap.keySet());
    }

    public void addSlaveNode(Rediser rediser) {
        slaveRediserMap.put(rediser.getAlias(), new InnerActiveRediser(rediser, false));
        slaveKeys = new ArrayList<>(slaveRediserMap.keySet());
    }

    public void start() {
        masterRedis.start();
        slaveRediserMap.forEach((k, v) -> v.start());

        if (null != masterRedis) {
            slaveRediserMap.values().forEach(e->{
                e.getRediser().slaveof(masterRedis.getRediser().getHostAndPort().getHost(), masterRedis.getRediser().getHostAndPort().getPort());
            });
        }
    }

    /**
     * 获取主库
     *
     * @return 主库实例
     */
    @Override
    public Rediser selectMasterRediser() {
        if (null != masterRedis && masterRedis.getActiveFlag()) {
            return masterRedis.getRediser();
        }

        throw new RediserException("主库没有设置或者不可用");
    }

    @Override
    public Rediser selectSlaveRediser() {
        Integer index = getNextIndex();
        if (null != index) {
            String dbAlias = slaveKeys.get(index);
            InnerActiveRediser innerActiveRediser;
            if (slaveRediserMap.containsKey(dbAlias)) {
                innerActiveRediser = slaveRediserMap.get(dbAlias);
            } else {
                throw new RediserException("从库获取失败");
            }
            return innerActiveRediser.getRediser();
        } else {
            // 从库都不可用，则采用主库，将主库添加到临时从库中
            Rediser rediser = selectMasterRediser();
            if (null != rediser) {
                log.warn(MS_LOG_PRE + "从库都不可用，走主库{}", rediser.getAlias());
                return selectSlaveRediser();
            }
            throw new RediserException("没有可用的库");
        }
    }

    @Override
    public void deActiveMaster(String alias) {
        if (!masterRedis.getRediser().getAlias().equals(alias)) {
            throw new RediserException("没有找到别名为" + alias + "的Redis实例");
        }

        // 去激活
        masterRedis.setActiveFlag(false);
        throw new RediserException("名为" + alias + "的Redis实例异常");
    }

    @Override
    public void deActiveSlave(String alias) {
        if (slaveRediserMap.containsKey(alias)) {
            slaveKeys.remove(alias);
            slaveIndex.set(0);
            slaveRediserMap.get(alias).setActiveFlag(false);
        } else {
            throw new RediserException("没有找到别名为" + alias + "的Redis实例");
        }

        // 启动恢复线程
        startRestore();
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

                doRestore(slaveRediserMap.values().stream().filter(e -> !e.getActiveFlag()).collect(Collectors.toList()));
            }
        });
    }

    private void doRestore(List<InnerActiveRediser> unActiveDbs) {
        if (!unActiveDbs.isEmpty()) {
            for (InnerActiveRediser innerActiveRediser : unActiveDbs) {
                String dbAlias = innerActiveRediser.getName();
                try {
                    innerActiveRediser.getRediser().get("k");

                    slaveRediserMap.get(dbAlias).setActiveFlag(true);
                    slaveKeys.add(dbAlias);
                    slaveIndex.set(0);
                } catch (Throwable ignore) {
                    log.info(MS_LOG_PRE + "尝试恢复从库{}失败", dbAlias);
                }
            }
        }
    }

    private Boolean haveUnActiveDb() {
        return slaveRediserMap.values().stream().anyMatch(e -> !e.getActiveFlag());
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
    static class InnerActiveRediser implements AutoCloseable {

        /**
         * 激活标示
         */
        private Boolean activeFlag = true;
        private String name;
        private Rediser rediser;
        /**
         * 主库还是从库
         */
        private Boolean masterOrSlave = true;

        InnerActiveRediser(Rediser rediser, Boolean masterOrSlave) {
            this.rediser = rediser;
            this.masterOrSlave = masterOrSlave;
            if (null != rediser) {
                this.name = rediser.getAlias();
            }
        }

        public void start() {
            if (null != rediser) {
                rediser.start();
            }
        }

        @Override
        public void close() {
            if (null != rediser) {
                rediser.close();
            }
        }
    }
}
