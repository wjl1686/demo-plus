package com.example.demo.common.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 抽象线程池构建
 *
 * @author wujl
 * @date 2020/10/18 23:01
 */
@Slf4j
public abstract class AbstractBuildThreadPoolTemplate {

    /**
     * 线程池构建初始化参数
     *
     * @return
     */
    public abstract ThreadPoolInitParam initParam();

    /**
     * 构建线程池
     *
     * @return
     */
    public ExecutorService buildPool() {
        ThreadPoolInitParam threadInitParam = initParam();
        ExecutorService executorService =
                new ThreadPoolExecutorTemplate(threadInitParam.getCorePoolNum(),
                        threadInitParam.getMaxPoolNum(),
                        threadInitParam.getKeepAliveTime(),
                        threadInitParam.getTimeUnit(),
                        threadInitParam.getWorkQueue(),
                        threadInitParam.getThreadFactory(),
                        threadInitParam.rejectedExecutionHandler);
        return executorService;
    }

    public ExecutorService buildFastPool() {
        ThreadPoolInitParam threadInitParam = initParam();
        TaskQueue taskQueue = new TaskQueue(threadInitParam.getCapacity());
        FastThreadPoolExecutor fastThreadPoolExecutor =
                new FastThreadPoolExecutor(threadInitParam.getCorePoolNum(),
                        threadInitParam.getMaxPoolNum(),
                        threadInitParam.getKeepAliveTime(),
                        threadInitParam.getTimeUnit(),
                        taskQueue,
                        threadInitParam.getThreadFactory(),
                        threadInitParam.rejectedExecutionHandler);
        taskQueue.setExecutor(fastThreadPoolExecutor);
        return fastThreadPoolExecutor;
    }

    @Data
    @Accessors(chain = true)
    public class ThreadPoolInitParam {
        /**
         * CPU 核数
         */
        private Integer cpuNum = Runtime.getRuntime().availableProcessors();
        /**
         * 核心线程数量
         */
        private Integer corePoolNum = cpuNum + 1;
        /**
         * 最大线程数量
         */
        private Integer maxPoolNum = cpuNum << 1;
        /**
         * 线程存活时间
         */
        private Long keepAliveTime = 30000L;
        /**
         * 线程存活时间单位
         */
        private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        /**
         * Maximum number of items in the deque
         */
        private Integer capacity = 500;
        /**
         * 阻塞队列
         */
        private BlockingQueue workQueue = new LinkedBlockingDeque(capacity);
        /**
         * 线程池任务满时拒绝任务策略
         */
        private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
        /**
         * 创建线程工厂
         */
        private ThreadFactory threadFactory;

        public ThreadPoolInitParam(String threadNamePrefix, boolean isDaemon) {
            this.threadFactory = new ThreadFactoryBuilder()
                    .setNameFormat(threadNamePrefix + "-%d")
                    .setDaemon(isDaemon)
                    .build();
        }
    }

}
