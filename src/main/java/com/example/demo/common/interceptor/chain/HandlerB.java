package com.example.demo.common.interceptor.chain;

public class HandlerB implements IHandler {
    @Override
    public void handle() {
        System.out.println("start - HandlerB");
    }
}
