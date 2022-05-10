package com.example.demo.common.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 提供全局获取AppContext的方法
 *
 * @author wujlong
 * @date 2019/7/21 18:56
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware, Ordered {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext get() {
        return applicationContext;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
