package com.example.demo.common.executor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 快速执行任务线程池
 * 必须配合 {@link TaskQueue}
 *
 * @author wujl
 * @date 2020/12/6 11:18
 */
@Slf4j
public class FastThreadPoolExecutor extends ThreadPoolExecutorTemplate {

    public FastThreadPoolExecutor(int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  TaskQueue<Runnable> workQueue,
                                  ThreadFactory threadFactory,
                                  RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    private final AtomicInteger submittedTaskCount = new AtomicInteger(0);

    public int getSubmittedTaskCount() {
        return submittedTaskCount.get();
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        submittedTaskCount.decrementAndGet();
    }

    @Override
    public void execute(@NotNull Runnable command) {
        submittedTaskCount.incrementAndGet();
        try {
            super.execute(command);
        } catch (RejectedExecutionException rx) {
            final TaskQueue queue = (TaskQueue) super.getQueue();
            try {
                if (!queue.retryOffer(command, 0, TimeUnit.MILLISECONDS)) {
                    submittedTaskCount.decrementAndGet();
                    throw new RejectedExecutionException("队列容量已满.", rx);
                }
            } catch (InterruptedException x) {
                submittedTaskCount.decrementAndGet();
                throw new RejectedExecutionException(x);
            }
        } catch (Exception t) {
            submittedTaskCount.decrementAndGet();
            throw t;
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = new AbstractBuildThreadPoolTemplate() {
            @Override
            public ThreadPoolInitParam initParam() {
                return new ThreadPoolInitParam("test-fast-executor-", false)
                        .setCorePoolNum(5)
                        .setMaxPoolNum(10);
            }
        }.buildFastPool();

        Integer max = 6;
        for (int i = 0; i < max; i++) {
            executorService.execute(new TestFastThread());
        }
        executorService.shutdown();
    }

    static class TestFastThread implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            log.info("  >>> 当前执行线程 :: {} ", Thread.currentThread().getName());
            Thread.sleep(3000);

        }
    }

}
