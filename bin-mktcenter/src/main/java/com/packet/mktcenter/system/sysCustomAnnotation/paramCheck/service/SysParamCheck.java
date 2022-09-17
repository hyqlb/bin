package com.packet.mktcenter.system.sysCustomAnnotation.paramCheck.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName SysParamCheck
 * AOP入参校验 自定义注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SysParamCheck {
    /**
     * 需要校验的参数下标
     * 默认为全部校验
     * @return
     */
    String paramIndex() default "";

    /**
     * 需要校验参数的校验类型
     * 0 校验参数是否为空
     * 1 校验参数是否为数字
     * @return
     */
    String paramCheckType() default "";
}
