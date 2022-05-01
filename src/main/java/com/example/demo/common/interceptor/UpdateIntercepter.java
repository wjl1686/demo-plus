package com.example.demo.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

@Slf4j
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class,
        Object.class}))
//@Component
public class UpdateIntercepter implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("start-update");

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        Class<?> clazz = parameter.getClass();
        String clazzName = clazz.getName();
//        if (clazzName.substring(clazzName.length() - 2,clazzName.length()).equals("Vo")){
        if (!clazz.getSuperclass().isInstance(Object.class)) {
            Class<?> superclass = clazz.getSuperclass();
            updateFeild(superclass.getDeclaredFields(), parameter, sqlCommandType);
        } else {
            updateFeild(parameter.getClass().getDeclaredFields(), parameter, sqlCommandType);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private void updateFeild(Field[] declaredFields, Object parameter, SqlCommandType sqlCommandType) throws IllegalAccessException {
        for (Field field : declaredFields) {
            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                if (field.getName().equals("createTime")) {
                    field.setAccessible(true);
                    field.set(parameter, new Date());
                }
            } else if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                if (field.getName().equals("updateTime")) {
                    field.setAccessible(true);
                    field.set(parameter, new Date());
                }
            }
        }
    }
}