package com.example.demo.common.executor;


import com.example.demo.common.base.ExtBaseDTO;
import com.example.demo.common.config.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * 快速同步执行器
 *
 * @author machen
 * @date 2020/11/5 14:18
 */
@Slf4j
public class FastSyncExecutor extends BaseSyncExecutor {

    private ExecutorService executorService;

    public FastSyncExecutor(ExtSyncAbstract extSyncAbstract) {
        super(extSyncAbstract);
        this.executorService = ApplicationContextHolder
                .get().getBean("extDataSyncExecutor", ExecutorService.class);
    }

    @Override
    public void executor(List<? extends ExtBaseDTO> dataList) {
        Callable callable = () -> {
            try {
                super.executor(dataList);
            } catch (Exception ex) {
                log.error("  >>> more-therad-逻辑执行异常 ", ex);
            }
            // 为后续流程预留位置
            return null;
        };
        executorService.submit(callable);
    }
}
