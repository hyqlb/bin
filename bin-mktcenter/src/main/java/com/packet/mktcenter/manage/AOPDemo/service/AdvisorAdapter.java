package com.packet.mktcenter.manage.AOPDemo.service;

/**
 * 将Advice转换为MethodInterceptor
 */
public interface AdvisorAdapter {
    MethodInterceptor getInterceptor(Advisor advisor);
}
