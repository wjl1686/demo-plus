package com.example.demo.common.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.NormalSegmentList;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * SQL特殊字符 % and _ 过滤
 *
 * @author machen
 * @date 2020/6/29 10:38
 */
@Slf4j
@Intercepts(
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
)
@Component
public class SqlSpecialCharsInterceptor implements Interceptor {

    private static final AtomicBoolean IS_PARAM_FLAG = new AtomicBoolean(false);

    private static final String LIKE = " like ";

    private static final String SPECIAL_CHAR = "?";

    private static final String PARAM_NAME = "param1";

    private static final String MYBATIS_PLUS_WRAPPER_FLAG = "ew";

    private static final String PARAMETER_OBJECT_STR = "parameterObject";

    private static final String KEY_NAME_FLAG = "ew.paramNameValuePairs.";

    /**
     * 上下文:
     *   1. 在搜索条件穿值为 % _ 时会被认为是通配符
     *   2. 所以在mybatis拦截器层做出拦截进行适配
     *
     * 为何代码如此之多:
     *   1. 重要的原因是因为我菜
     *   2. 其次,mybatis-plus和mybatis的sql封装完全不同
     *   3. 如果你会看下面的代码会发现,其实就是两套逻辑
     *   4. 不优雅的代码也有很多,打个 TODO
     *   5. 能够实现效果全靠硬写（反射【有些真的是硬写】 强制类型转换【写项目也有几个了,都没有这几行代码强转用得多】）
     *
     * 注意事项❗️❗️❗
     *   1. 如果你是因为写了xml文件SQL报错看到这里的,提前告诉你一个可能会出错的点
     *   2. 当你在DAO接口上的参数列表中 不写@Param()或者只写一个那么没有问题
     *   3. 如果你写了两个@Param()注解,恰巧第二个注解中的某个参数用到了 % _ 这两个字符
     *   4. sorry,这个程序虽然不至于报错,但是也不会起到原有的SQL拦截的效果
     *   5. 解决办法就是将第二个@Param()的模糊匹配字段加入到第一个@Param()中
     *   6. 当然,既然这么设计了,就是感觉不会有列表查询同时传入两个ReqDTO进行查询的
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("start-spe-query");

        IS_PARAM_FLAG.set(false);
        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = statement.getBoundSql(parameterObject);
        String sql = boundSql.getSql();

        AbstractWrapper wrapper = null;
        NormalSegmentList normal = null;
        Map<String, Object> parameterObjectMap = null;
        boolean isMybatisPlusRequest = parameterObject instanceof MapperMethod.ParamMap && (parameterObjectMap = (Map<String, Object>) boundSql.getParameterObject()).containsKey(MYBATIS_PLUS_WRAPPER_FLAG);

        if (isMybatisPlusRequest) {
            wrapper = (AbstractWrapper) parameterObjectMap.get(MYBATIS_PLUS_WRAPPER_FLAG);
            normal = wrapper.getExpression().getNormal();
        }
        Map<String, Object> resultMap = modifyLikeSql(sql, parameterObject, boundSql, normal);

        // 无模糊匹配过滤字符 正常执行
        if (CollectionUtil.isEmpty(resultMap)) {
            return invocation.proceed();
        }

        // 判断为 mybatis请求 或是 mybatis-plus
        if (isMybatisPlusRequest) {
            Map<String, Object> paramNameValuePairs = wrapper.getParamNameValuePairs();
            resultMap.forEach((key, val) -> paramNameValuePairs.put(key, val));

            parameterObjectMap.put(MYBATIS_PLUS_WRAPPER_FLAG, wrapper);
            boundSql.setAdditionalParameter(PARAMETER_OBJECT_STR, parameterObjectMap);
        } else {
            Field[] declaredFields = parameterObject instanceof MapperMethod.ParamMap
                    ? ((MapperMethod.ParamMap) parameterObject).get(PARAM_NAME).getClass().getDeclaredFields()
                    : parameterObject.getClass().getDeclaredFields();

            for (Field field : declaredFields) {
                if (resultMap.containsKey(field.getName())) {
                    ReflectionUtils.makeAccessible(field);
                    if (IS_PARAM_FLAG.get()) {
                        ReflectionUtils.setField(field, ((MapperMethod.ParamMap) parameterObject).get(PARAM_NAME), resultMap.get(field.getName()));
                    } else {
                        ReflectionUtils.setField(field, parameterObject, resultMap.get(field.getName()));
                    }

                }
            }
        }

        return invocation.proceed();
    }

    @SuppressWarnings("unchecked")
    private Map modifyLikeSql(String sql, Object parameterObject, BoundSql boundSql, NormalSegmentList normal) {
        if (!sql.toLowerCase().contains(LIKE) || !sql.toLowerCase().contains(SPECIAL_CHAR)) {
            return Maps.newHashMap();
        }

        // 获取关键字的个数（去重）
        String[] strList = sql.split("\\?");
        Set<String> keyNames = new HashSet<>();
        for (int i = 0; i < strList.length; i++) {
            if (strList[i].toLowerCase().contains(LIKE)) {
                String keyName = boundSql.getParameterMappings().get(i).getProperty();
                keyNames.add(keyName);
            }
        }

        HashMap resultMap = Maps.newHashMap();
        // 对关键字进行特殊字符“清洗”，如果有特殊字符的，在特殊字符前添加转义字符（\）
        for (String keyName : keyNames) {
            if (keyName.contains(KEY_NAME_FLAG) && sql.toLowerCase().contains(LIKE + SPECIAL_CHAR)) {
                Map<String, Object> parameter = objectToMap(parameterObject, false);
                // 在业务层进行条件构造产生的模糊查询关键字
                QueryWrapper wrapper = JSON.parseObject(JSON.toJSONString(parameter.get(MYBATIS_PLUS_WRAPPER_FLAG)), QueryWrapper.class);
                parameter = wrapper.getParamNameValuePairs();

                String[] keyList = keyName.split("\\.");
                // ew.paramNameValuePairs.MPGENVAL1，截取字符串之后，获取第三个，即为参数名
                Object keyObj = parameter.get(keyList[2]);
                if (isSqlContain(keyObj)) {
                    // 获取 mybatis-plus 模糊方式
                    SqlLike sqlLike = analysisSqlLike(normal);
                    String prefixStr = "";
                    String lastStr = "";
                    int startIdx = 0;

                    if (Objects.equals(sqlLike.name(), SqlLike.DEFAULT.name())) {
                        prefixStr = "%"; lastStr = "%"; startIdx = 1;
                    } else if (Objects.equals(sqlLike.name(), SqlLike.RIGHT.name())) {
                        lastStr = "%";
                    } else if (Objects.equals(sqlLike.name(), SqlLike.LEFT.name())) {
                        prefixStr = "%"; startIdx = 1;
                    }

                    resultMap.put(keyList[2], prefixStr + escapeChar(keyObj.toString().substring(startIdx, keyObj.toString().length() - 1)) + lastStr);
                }
            } else {
                Map<String, Object> parameter = objectToMap(parameterObject, true);
                if (keyName.contains(".")) {
                    int idx = keyName.lastIndexOf(".");
                    keyName = keyName.substring(idx + 1);
                }
                Object keyObj = parameter.get(keyName);
                if (isSqlContain(keyObj)) {
                    resultMap.put(keyName, escapeChar(keyObj.toString()));
                }
            }
        }
        return resultMap;
    }

    private SqlLike analysisSqlLike(NormalSegmentList normal) {
        SqlLike sqlLike = null;
        for (int i = 1; i < normal.size(); i++) {
            if (normal.get(i) instanceof SqlKeyword) {
                SqlKeyword sqlKeyword = (SqlKeyword) normal.get(i);
                if (Objects.equals(sqlKeyword.getSqlSegment(), "LIKE")) {
                    try {
                        ISqlSegment abstractWrapper = normal.get(i + 1);
                        Class<? extends ISqlSegment> aClass = normal.get(i + 1).getClass();
                        Field field = aClass.getDeclaredField("arg$3");
                        ReflectionUtils.makeAccessible(field);
                        sqlLike = (SqlLike) field.get(abstractWrapper);
                    } catch (Exception ex) {
                        log.error("  >>> mybatis-plus 解析模糊方式报错", ex);
                        // ignore
                    }
                }
            }
        }
        return sqlLike;
    }

    private Map<String, Object> objectToMap(Object obj, boolean isMybatisRequest) {
        Map<String, Object> resultMap = JSONObject.parseObject(JSONObject.toJSONString(obj), HashMap.class);
        if (isMybatisRequest) {
            for(Map.Entry<String, Object> entry : resultMap.entrySet()){
                if (entry.getValue() instanceof JSONObject) {
                    IS_PARAM_FLAG.set(true);
                }
            }

            if (IS_PARAM_FLAG.get()) {
                JSONObject jsonObject = (JSONObject) resultMap.get(PARAM_NAME);
                resultMap = jsonObject.getInnerMap();
            }
        }

        return resultMap;
    }

    private String escapeChar(String before) {
        if (StringUtils.isNotBlank(before)) {
            before = before.replaceAll("\\\\", "\\\\\\\\");
            before = before.replaceAll("_", "\\\\_");
            before = before.replaceAll("%", "\\\\%");
        }
        return before;
    }

    private boolean isSqlContain(Object keyObj) {
        return keyObj instanceof String && (keyObj.toString().contains("_") || keyObj.toString().contains("\\") || keyObj.toString().contains("%"));
    }

}
