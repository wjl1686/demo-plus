package com.example.demo.common.interceptor.chain;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class HandlerChain {
    List<IHandler> handlerList = new ArrayList();

    public void addHandler(List<IHandler> handlers) {
        handlerList.addAll(handlers);
    }

    public void handle() {
        handlerList.forEach(IHandler::handle);
    }

    public static void main(String[] args) {
        HandlerChain handlerChain = new HandlerChain();
        handlerChain.addHandler(Lists.newArrayList(new HandlerA(), new HandlerB()));
        handlerChain.handle();
        /**
         * 程序执行结果：
         * HandlerA打印：执行 HandlerA
         * HandlerB打印：执行 HandlerB
         */
    }

}
