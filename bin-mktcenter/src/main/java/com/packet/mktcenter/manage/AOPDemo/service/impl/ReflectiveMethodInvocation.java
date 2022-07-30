package com.packet.mktcenter.manage.AOPDemo.service.impl;

import com.packet.mktcenter.manage.AOPDemo.service.MethodInterceptor;
import com.packet.mktcenter.manage.AOPDemo.service.MethodInvocation;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * 处理切面逻辑的地方
 */
public class ReflectiveMethodInvocation implements MethodInvocation {
    // 代理对象
    private Object proxy;
    // 目标对象
    private Object target;
    // 目标方法
    private Method method;
    // 一连串的前置通知器，都封装成MethodInterceptor
    protected final List<?> beforeInterceptorsAndDynamicMethodMatchers;
    // 一连串的后置通知器，都封装成MethodInterceptor
    protected final List<?> afterInterceptorsAndDynamicMethodMatchers;

    private Object[] aguments;
    // 代表执行到第几个通知器链
    private int currentBeforeInterceptorIndex = -1;

    private int currentAfterInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] aguments, List<?> beforeInterceptorsAndDynamicMethodMatchers, List<?> afterInterceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.aguments = aguments;
        this.beforeInterceptorsAndDynamicMethodMatchers = beforeInterceptorsAndDynamicMethodMatchers;
        this.afterInterceptorsAndDynamicMethodMatchers = afterInterceptorsAndDynamicMethodMatchers;
    }

    @Override
    public Object proceed() {
        Object obj = null;
        int methodBeforeIndex = beforeInterceptorsAndDynamicMethodMatchers.size() - 1;
        if (methodBeforeIndex > this.currentBeforeInterceptorIndex){
            // 这里就是将advice封装成MethodInterceptor，从链表中一个一个执行
            Object interceptorOrInterceptionAdvice = this.beforeInterceptorsAndDynamicMethodMatchers.get(++this.currentBeforeInterceptorIndex);
            // 这里将ReflectiveMethodInvocation自己用传入了，是一个递归调用
            return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
        }
        if (Objects.equals(this.currentBeforeInterceptorIndex, methodBeforeIndex)) {
            try {
                // 目标方法的最终调用
                obj =  method.invoke(target, aguments);
                ++this.currentBeforeInterceptorIndex;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int methodAfterIndex = afterInterceptorsAndDynamicMethodMatchers.size() - 1;
        if (methodAfterIndex > this.currentAfterInterceptorIndex){
            // 这里就是将advice封装成MethodInterceptor，从链表中一个一个执行
            Object interceptorOrInterceptionAdvice = this.afterInterceptorsAndDynamicMethodMatchers.get(++this.currentAfterInterceptorIndex);
            // 这里将ReflectiveMethodInvocation自己用传入了，是一个递归调用
            return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
        }
        return obj;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public Object[] getArguments() {
        return aguments;
    }
}
