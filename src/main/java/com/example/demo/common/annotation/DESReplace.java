package com.example.demo.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DESReplace {

    /**
     * 字段名称
     *
     * @return
     */
    String fieldName() default "";
}
