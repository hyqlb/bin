package com.packet.mktcenter.manage.AOPDemo.service;

import java.lang.reflect.Method;
/**
 * 通知
 */
public interface MethodAdvice extends Advice {

    void sendMsg(Method method, Object[] args, Object target);
}
