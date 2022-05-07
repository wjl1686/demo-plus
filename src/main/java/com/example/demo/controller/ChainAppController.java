package com.example.demo.controller;


import com.example.demo.common.interceptor.chain.handler.VerifyHandlerChain;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@RestController
@RequestMapping("/api")
class ChainAppController {

    @Autowired
    private VerifyHandlerChain verifyHandlerChain;

    @RequestMapping(value = "/test/chain", method = GET)
    void contextLoads() {
        List<Object> verify = verifyHandlerChain.verify(Lists.newArrayList("源码兴趣圈", "@龙台"));
        System.out.println(verify);
    }
}
