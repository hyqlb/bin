package com.packet.mktcenter.manage.AOPDemo.service;

/**
 * 方法拦截，这里可以进行方法拦截
 */
public interface MethodInterceptor {

    Object invoke(MethodInvocation invocation);
}
