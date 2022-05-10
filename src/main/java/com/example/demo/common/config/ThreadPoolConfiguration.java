package com.example.demo.common.config;



import com.example.demo.common.executor.AbstractBuildThreadPoolTemplate;
import com.example.demo.common.executor.SyncRejectedExecutionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 线程池资源管理
 *
 * @author wujlong
 * @date 2020/10/18 23:19
 */
@Slf4j
@Configuration
public class ThreadPoolConfiguration {

    @Bean("extDataSyncExecutor")
    public ExecutorService dataIsuExecutor() {
        return new AbstractBuildThreadPoolTemplate() {
            @Override
            public ThreadPoolInitParam initParam() {
                return new ThreadPoolInitParam("extDataSyncExecutor", false)
                        .setCorePoolNum(Runtime.getRuntime().availableProcessors())
                        .setMaxPoolNum(Runtime.getRuntime().availableProcessors() << 1)
                        .setRejectedExecutionHandler(new SyncRejectedExecutionHandler())
                        .setKeepAliveTime(60000L)
                        .setWorkQueue(new LinkedBlockingDeque(500));
            }
        }.buildFastPool();
    }

    @Bean("initMcsProdAllStreamSyncExecutor")
    public ExecutorService queryTendInfoExecutor() {
        return new AbstractBuildThreadPoolTemplate() {
            @Override
            public ThreadPoolInitParam initParam() {
                return new ThreadPoolInitParam("query-TendInfo-Executor", false)
                        .setCorePoolNum(Runtime.getRuntime().availableProcessors())
                        .setMaxPoolNum(Runtime.getRuntime().availableProcessors() << 1)
                        .setRejectedExecutionHandler(new SyncRejectedExecutionHandler())
                        .setKeepAliveTime(60000L)
                        .setWorkQueue(new LinkedBlockingDeque(1000));
            }
        }.buildPool();
    }
}
