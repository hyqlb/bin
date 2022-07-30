package com.packet.mktcenter.manage.AOPDemo.service.impl;

import com.packet.mktcenter.manage.AOPDemo.service.MethodAdvice;
import com.packet.mktcenter.manage.AOPDemo.service.MethodInterceptor;
import com.packet.mktcenter.manage.AOPDemo.service.MethodInvocation;

public class MethodAdviceInterceptor implements MethodInterceptor {

    private MethodAdvice advice;

    public MethodAdviceInterceptor(MethodAdvice advice){
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) {
        advice.sendMsg(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}
