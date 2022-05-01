package com.example.demo.common.executor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 重写拒绝策略
 * 提交阻塞队列为同步提交, 保证不丢任务
 *
 * @author wujl
 * @date 2020/12/6 12:20
 */
@Slf4j
public class SyncRejectedExecutionHandler implements RejectedExecutionHandler {

    @SneakyThrows
    @Override
    public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
        log.info("  >>> 当前执行线程 :: {} ", Thread.currentThread().getName());
        executor.getQueue().put(task);
    }

}
