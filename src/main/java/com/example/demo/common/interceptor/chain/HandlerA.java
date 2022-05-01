package com.example.demo.common.interceptor.chain;

public class HandlerA implements IHandler {
    @Override
    public void handle() {
        System.out.println("start - HandlerA");
    }
}
