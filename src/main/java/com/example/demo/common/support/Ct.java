package com.example.demo.common.support;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Ct implements TT {
    /**
     * 执行下发任务
     *
     * @param sourceList
     * @param isuTaskId
     */
    @Override
    public void executeIssue(List sourceList, String isuTaskId) {

    }

    /**
     * 定义标识
     *
     * @return
     */
    @Override
    public String supports() {
        return "10000";
    }

    /**
     * 查询同步信息
     *
     * @param dataBizId
     * @param provCode
     */
    @Override
    public List executeQuery(String dataBizId, String provCode) {
        return null;
    }
}
