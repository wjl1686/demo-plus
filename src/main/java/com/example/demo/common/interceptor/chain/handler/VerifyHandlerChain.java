package com.example.demo.common.interceptor.chain.handler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class VerifyHandlerChain implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    public final List<VerifyHandler> verifyHandlerList = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, VerifyHandler> beansOfType = applicationContext.getBeansOfType(VerifyHandler.class);
        beansOfType.forEach((key, val) -> verifyHandlerList.add(val));
    }

    public List<Object> verify(List<Object> allObjectList) {
        List<Object> resultList = null;
        for (VerifyHandler verifyHandler : verifyHandlerList) {
            resultList = verifyHandler.verify(allObjectList);
            if (CollectionUtils.isEmpty(resultList)) {
                return resultList;
            }
        }
        return resultList;
    }
}
