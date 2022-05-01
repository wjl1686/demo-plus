package com.example.demo.task.support;

import java.util.List;

public interface TT<T> {

    /**
     * 执行下发任务
     *
     * @param sourceList
     */
    public abstract void executeIssue(List sourceList, String isuTaskId);

    /**
     * 定义标识
     *
     * @return
     */
    public abstract String supports();

    /**
     * 查询同步信息
     *
     * @param dataBizId
     */
    public abstract List<T> executeQuery(String dataBizId, String provCode);
}
