package com.packet.mktcenter.manage.AOPDemo.service.impl;

import com.packet.mktcenter.manage.AOPDemo.service.Advisor;
import com.packet.mktcenter.manage.AOPDemo.service.AdvisorAdapter;
import com.packet.mktcenter.manage.AOPDemo.service.MethodAdvice;
import com.packet.mktcenter.manage.AOPDemo.service.MethodInterceptor;

public class MethodAdviceAdapter implements AdvisorAdapter {
    @Override
    public MethodInterceptor getInterceptor(Advisor advisor) {
        MethodAdvice advice = (MethodAdvice) advisor.getAdvice();
        return new MethodAdviceInterceptor(advice);
    }
}
