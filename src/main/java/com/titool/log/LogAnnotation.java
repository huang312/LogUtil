package com.titool.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Descrpition 日志注解
 * @Date 2024/8/11
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    /**
     * 业务描述
     */
    String desc() default "";

    /**
     * 关键参数索引
     */
    int keyArgIndex() default -1;

    String[] keyArgsProps() default {};
}
