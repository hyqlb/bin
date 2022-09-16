package com.packet.mktcenter.system.sysCustomAnnotation.customLog.service;

import java.lang.annotation.*;

/**
 * ClassName SystemCrmlog
 * AOP日志记录 自定义注解类
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemCrmlog {
    /**
     * 日志描述
     * 对于什么表格进行操作
     * @return
     */
    String description() default "";

    /**
     * 操作了的表名
     * @return
     */
    String tableName() default "";
}
