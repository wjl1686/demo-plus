package com.example.demo.common.interceptor.chain.handler;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 非空数据数据校验
 *
 * @author wujl2
 * @date 2022-03-28 13:39:57
 */
@Component
public class EmptyVerifyHandler implements VerifyHandler {
    @Override
    public List<Object> verify(List<Object> allObjectList) {
        //数据校验
        System.out.println("start-EmptyVerifyHandler");
        List<Object> truetList = allObjectList;
        return truetList;
    }
}
