package com.example.demo.common.plugin;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.demo.common.base.ExtBaseDTO;
import com.example.demo.common.base.ExtFieldNotNull;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 检查外部数据字段是否为空
 * TODO: 后续会使用责任链模式多重校验
 *
 * @author machen
 * @date 2020/10/19 11:15
 */
@Component
public class CheckDataIsNullService {

    /**
     * 本地 Field 缓存
     */
    private static final Map<String, List<Field>> FIELD_NOT_NULL_CACHE = Maps.newConcurrentMap();

    private static final String SUFFIX_STR = "等数据元信息不标准, 无法进入招采库";

    /**
     * 扫描类中定义注解, 是否标准符合入库标准
     *
     * @param sources
     * @param <T>
     */
    @SneakyThrows
    public <T extends ExtBaseDTO> Map judgeNormAndRemarkReturnMap(List<T> sources) {
        Object fieldData = null;
        Field[] fields = null;
        List<T> trueList, falseList = null;
        List<Field> fieldNotNullList = null;
        Map<Boolean, List<T>> resultMap = new ImmutableMap
                .Builder<Boolean, List<T>>()
                .put(Boolean.TRUE, trueList = new ArrayList(sources.size()))
                .put(Boolean.FALSE, falseList = new ArrayList(sources.size()))
                .build();

        for (T source : sources) {
            if (CollectionUtil.isEmpty(fieldNotNullList)
                    && CollectionUtil.isEmpty(fieldNotNullList = FIELD_NOT_NULL_CACHE.get(source.getClass().getName()))) {
                fields = source.getClass().getDeclaredFields();
                fieldNotNullList = Arrays.stream(fields)
                        .filter(field -> !ObjectUtils.isEmpty(field.getAnnotation(ExtFieldNotNull.class)))
                        .collect(Collectors.toList());
                // 增加本地缓存
                FIELD_NOT_NULL_CACHE.put(source.getClass().getName(), fieldNotNullList);
            }

            boolean isTrue = true;
            List<String> extFieldNotNullList = new ArrayList(fieldNotNullList.size());

            for (Field field : fieldNotNullList) {
                ReflectionUtils.makeAccessible(field);
                fieldData = ReflectionUtils.getField(field, source);
                if (Objects.isNull(fieldData) || StringUtils.isBlank(fieldData.toString())) {
                    isTrue = false;
                    // 添加数据校验项不通过原因
                    ExtFieldNotNull extFieldNotNull = field.getAnnotation(ExtFieldNotNull.class);
                    extFieldNotNullList.add(extFieldNotNull.name());
                }
            }

            if (CollectionUtil.isNotEmpty(extFieldNotNullList)) {
                String remarks = Joiner.on("、").join(extFieldNotNullList);
                source.setRemark(remarks + SUFFIX_STR);
            }

            if (isTrue) {
                trueList.add(source);
            } else {
                falseList.add(source);
            }
        }
        return resultMap;
    }
}
