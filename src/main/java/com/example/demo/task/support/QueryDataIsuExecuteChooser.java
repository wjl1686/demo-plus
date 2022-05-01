package com.example.demo.task.support;

import com.example.demo.common.config.ApplicationContextHolder;
import com.google.common.collect.Maps;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 数据下发执行策略
 *
 * @author wujlong
 * @date 2021/2/14 上午11:05
 */

@Component
public class QueryDataIsuExecuteChooser implements ApplicationRunner {

    private final Map<String, AbstractDataIsuExecuteStrategy> chooseMap = Maps.newHashMap();

    public AbstractDataIsuExecuteStrategy choose(String type) {
        return chooseMap.get(type);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ApplicationContext applicationContext = ApplicationContextHolder.get();
        Map<String, AbstractDataIsuExecuteStrategy> solverMap = applicationContext.getBeansOfType(AbstractDataIsuExecuteStrategy.class);
        solverMap.values().forEach(each -> chooseMap.put(each.supports(), each));
    }
}
