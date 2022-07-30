package com.packet.mktcenter.manage.AOPDemo.service.impl;

import com.packet.mktcenter.manage.AOPDemo.model.AdvisedSupport;
import com.packet.mktcenter.manage.AOPDemo.service.AopProxy;
import com.packet.mktcenter.manage.AOPDemo.service.MethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    // 用于创建代理对象所需要的原材料
    private AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport){
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), advisedSupport.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodInvocation invocation;
        // 获取到所有的调用器链
        List<Object> beforeChain = this.advisedSupport.getBeforeInterceptorsAndDynamicInterceptionAdvice();
        List<Object> afterChain = this.advisedSupport.getAfterInterceptorsAndDynamicInterceptionAdvice();
        invocation = new ReflectiveMethodInvocation(proxy, advisedSupport.getTarget(), method, args, beforeChain, afterChain);
        return invocation.proceed();
    }
}
