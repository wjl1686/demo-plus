package com.example.demo.common.interceptor;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.demo.common.annotation.DESReplace;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;


@Slf4j
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
public class QueryScopeInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("start-scope-query");
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String sqlId = mappedStatement.getId();
        log.debug("------sqlId------" + sqlId);
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        log.debug("------sqlCommandType------" + sqlCommandType);

        Object parameter = invocation.getArgs()[1];
        if (SqlCommandType.SELECT != sqlCommandType) {
            return invocation.proceed();
        }
        List<Object> results = (List<Object>) invocation.proceed();
        if (CollectionUtils.isNotEmpty(results)) {
            Class<?> cls = results.get(0).getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Object result : results) {
                for (Field field : fields) {
                    //获取我们自定义的注解
                    Field idField = null;
                    DESReplace desReplace = field.getAnnotation(DESReplace.class);
                    if (desReplace != null) {//如果存在这个注解 我们在执行后续方法
                        String fieldName = field.getName();
                        idField = cls.getDeclaredField(fieldName);
                        idField.setAccessible(true);//允许我们在用反射时访问私有变量
                        field.setAccessible(true);
                        Object strEncode = idField.get(result);
                        //String content = QuicklyEncryptToolUtils.decode(HsafFieldCipherTool.class, strEncode.toString());
                        field.set(result, "content");  //用字典id查询出字典名称 并替换结果集中的值
                    }
                }
            }
        }

        return results;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub
    }
}
