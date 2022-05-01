package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.ReturnT;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * 以流式查询的方式同步全量耗材信息（初始化）
 *
 * @author wujl2
 * @date 2021/3/8 15:02
 */
@Slf4j
@Component
@RestController
public class InitMcsProdAllStreamSyncJobHandler {

    @Autowired
    private UserMapper mcsProdDAO;


    /**
     * 封装后的线程池
     */
    @Autowired
    private ExecutorService initMcsProdAllStreamSyncExecutor;

    /**
     * 单次同步 ES 数量
     */
    private Integer MAX_SYNC_SIZE = 5000;

    /**
     * 阻塞队列最大容量, 相当于一个缓冲池大小
     */
    private Integer MAX_POOL_SIZE = 200000;

    /**
     * 记录开始时间
     */
    private Long START_TIME = 0L;

    /**
     * 记录同步
     */
    private final AtomicInteger COUNT_NUM = new AtomicInteger(0);

    /**
     * 记录实际同步数量
     */
    private final LongAdder SYNC_SUM = new LongAdder();

    /**
     * 打印输出监控定时器
     */
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    protected String getIndexName() {
        return "bdc-mcsprod";
    }

    protected String getKey() {
        return "";
    }


    @Value("${es.prefix:1}")
    private String indexPrefix;


    @GetMapping("api/test/mcsProd/stream")
    public ReturnT<String> execute(String param) throws Exception {


        // 打印输出线程池监控、定时同步数量
        printPoolAndScheduledInfo();


        // 执行耗材同步 ES 程序
        executeMcsProdSyncEs();


        // 释放定时器、线程池资源
        shutdownPoolAndPrintCountSize();


        return ReturnT.SUCCESS;
    }

    private void executeMcsProdSyncEs() {
        BlockingQueue<User> blockingQueueCachePool = new LinkedBlockingDeque(MAX_POOL_SIZE);
        mcsProdDAO.userAllDataStreamQuery(resultContext -> {
            int temp = 0;
            int queueSize = 0;
            // 记录流式查询总数量
            COUNT_NUM.incrementAndGet();
            // 每次向缓冲池添加 MAX_SYNC_SIZE 记录
            try {
                blockingQueueCachePool.put(resultContext.getResultObject());
            } catch (Exception ex) {
                // ignore
                log.error(" [Error] 基础数据同步耗材数据 -> ElasticSearch 添加阻塞队列缓冲池失败, 数据记录 :: {}",
                        JSON.toJSONString(resultContext.getResultObject()), ex);
            }
            // 避免请求 ES 次数过多, 所以建议每次 MAX_SYNC_SIZE 条数, 虽然可能不够这个数
            if ((temp = (queueSize = blockingQueueCachePool.size()) % MAX_SYNC_SIZE) >= 0 && temp != queueSize) {
                initMcsProdAllStreamSyncExecutor.execute(() -> executeTask(blockingQueueCachePool));
            }
        });

        // 兜底, 将最后缓冲的任务执行
        initMcsProdAllStreamSyncExecutor.execute(() -> executeTask(blockingQueueCachePool));
    }

    private void executeTask(BlockingQueue<User> blockingQueueCachePool) {
        List<User> copyList = new ArrayList(MAX_SYNC_SIZE);

        try {
            int drainTo = blockingQueueCachePool.drainTo(copyList, MAX_SYNC_SIZE);
            if (drainTo > 0) {
                //template.syncSave(copyList);
                //记录时间
                saveLastUpdateTime();
                SYNC_SUM.add(drainTo);
            }
        } catch (Exception ex) {
            log.error(" [Error] 基础数据同步耗材数据 -> ElasticSearch 执行失败", ex);
        } finally {
            // copyList = null;
        }
    }

    private void printPoolAndScheduledInfo() {
        START_TIME = System.currentTimeMillis();
        try {
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                printNumAndUpdateMiddle();
                printPool((ThreadPoolExecutor) initMcsProdAllStreamSyncExecutor);
            }, 30, 10, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("ex={}" + e);
        }

    }

    private void printPool(ThreadPoolExecutor threadPoolExecutor) {
        log.info(" [Info] 线程池状态打印, [当前活动线程数] :: {}, [当前排队任务数] :: {}, [执行完成线程数] :: {}, [线程池任务总数] :: {}",
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getTaskCount());
    }

    private void printNumAndUpdateMiddle() {
        log.info(" [Info] 基础数据初始化耗材数据 -> ElasticSearch 当前已同步总数量 :: {}", COUNT_NUM.get());
    }

    private void shutdownPoolAndPrintCountSize() {
        // 关闭定时器
        scheduledExecutorService.shutdown();
        // 关闭线程池
        initMcsProdAllStreamSyncExecutor.shutdown();
        while (true) {
            if (scheduledExecutorService.isTerminated() && initMcsProdAllStreamSyncExecutor.isTerminated()) {
                log.info(" 🚀 🚀 🚀 基础数据初始化耗材数据 -> ElasticSearch 总条数 :: {}, SYNC_SUM :: {}, 耗材同步 ES 总耗时 :: {}",
                        COUNT_NUM.get(),
                        SYNC_SUM.longValue(),
                        millisToStringShort(System.currentTimeMillis() - START_TIME));
                break;
            }
        }
    }

    private String millisToStringShort(Long time) {
        StringBuilder sb = new StringBuilder();
        long millis = 1;
        long seconds = 1000 * millis;
        long minutes = 60 * seconds;
        long hours = 60 * minutes;
        long days = 24 * hours;
        if (time % days % hours / minutes >= 1) {
            sb.append((int) (time % days % hours / minutes) + "分钟");
        }
        if (time % days % hours % minutes / seconds >= 1) {
            sb.append((int) (time % days % hours % minutes / seconds) + "秒");
        }
        return sb.toString();
    }

    /**
     * 保存最后更新时间
     *
     * @param
     */
    private void saveLastUpdateTime() {
        log.info("[定时任务] 同步数据到索引：{}，记录最后更新时间：{}", getCacheKey(), new Date());
        //cacheUtil.set(getCacheKey(), new Date(), 365, TimeUnit.DAYS);
    }


    /**
     * 组装缓存的key
     *
     * @return
     */
    private String getCacheKey() {
        String index = new StringBuffer(indexPrefix).append(getIndexName()).toString();
        if (getKey().isEmpty()) {
            return String.format("elasticsearch:sync:%s", index);
        } else {
            return String.format("elasticsearch:sync:%s:%s", index, getKey());
        }
    }

}