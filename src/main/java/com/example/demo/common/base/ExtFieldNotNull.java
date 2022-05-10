package com.example.demo.common.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 判断外部数据字段不可为空标志
 *
 * @author wujlong
 * @date 2020/8/21 17:15
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtFieldNotNull {

    /**
     * 字段名称
     *
     * @return
     */
    String name() default "";

}
