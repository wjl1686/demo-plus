package com.example.demo.common.executor;

import com.example.demo.common.base.ExtBaseDTO;

import java.util.List;

/**
 * 同步执行器
 *
 * @author wujlong
 * @date 2020/11/5 14:16
 */
public interface SyncExecutor {

    void executor(List<? extends ExtBaseDTO> dataList);
}
