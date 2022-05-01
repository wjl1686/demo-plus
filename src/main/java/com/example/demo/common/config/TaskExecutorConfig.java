package com.example.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @desc 线程池配置
 * @author jiapf
 * @since 2019-8-12
 * @version 1.0.0
 */
@EnableAsync
@Configuration
public class TaskExecutorConfig {

    /**
     * 线程池维护线程的最少数量
     */
    private  static final int CORE_POOL_SIZE = 5;

    /**
     * 线程池维护线程的最大数量
     */
    private  static final int MAX_POOL_SIZE = 500;

    /**
     * 线程池
     * @return
     */
    @Bean(name="threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        /**
         * 线程池维护线程的最少数量
         */
        threadPoolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        /**
         * 线程池维护线程的最大数量
         */
        threadPoolTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        /**
         *线程池所使用的缓冲队列
         */
        threadPoolTaskExecutor.setQueueCapacity(200);
        /**
         * 线程池维护线程所允许的空闲时间
         */
        threadPoolTaskExecutor.setKeepAliveSeconds(30000);
        threadPoolTaskExecutor.setThreadNamePrefix("Tender-Executive-");
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //threadPoolTaskExecutor.setTaskDecorator(new ExecutorTaskDecorator());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

   /* private class ExecutorTaskDecorator implements TaskDecorator {
        @Override
        public Runnable decorate(Runnable r) {
            // copy User context
         *//*   Optional<UserContext> userContext = RequestContext.getUserContext();
            // copy Request context
            RequestAttributes requestContext = RequestContextHolder.getRequestAttributes();
            return () -> {
                try {
                    RequestContext.setUserContext(userContext.orElse(null));
                    RequestContextHolder.setRequestAttributes(requestContext);
                    r.run();
                } finally {
                    RequestContext.removeUserContext();
                    RequestContextHolder.resetRequestAttributes();
                }
            };*//*
            return threadPoolTaskExecutor().newThread();
        }
    }*/

}
