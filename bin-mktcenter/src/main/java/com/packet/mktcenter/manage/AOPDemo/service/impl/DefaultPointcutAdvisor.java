package com.packet.mktcenter.manage.AOPDemo.service.impl;

import com.packet.mktcenter.manage.AOPDemo.model.Pointcut;
import com.packet.mktcenter.manage.AOPDemo.service.Advisor;
import com.packet.mktcenter.manage.AOPDemo.service.Advice;

/**
 * Advisor的默认实现类
 */
public class DefaultPointcutAdvisor implements Advisor {

    private Pointcut pointcut;
    private Advice advice;
    private String advisorType;

    public DefaultPointcutAdvisor(Pointcut pointcut, Advice advice, String advisorType){
        this.pointcut = pointcut;
        this.advice = advice;
        this.advisorType = advisorType;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public String getAdvisorType() {
        return this.advisorType;
    }
}
