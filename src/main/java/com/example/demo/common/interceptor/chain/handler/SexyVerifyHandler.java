package com.example.demo.common.interceptor.chain.handler;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 敏感词数据数据校验
 *
 * @author wujl2
 * @date 2022-03-28 13:39:57
 */
@Component
public class SexyVerifyHandler implements VerifyHandler {
    @Override
    public List<Object> verify(List<Object> allObjectList) {
        //数据校验
        System.out.println("start-SexyVerifyHandler");
        List<Object> truetList = allObjectList;
        return truetList;
    }
}
