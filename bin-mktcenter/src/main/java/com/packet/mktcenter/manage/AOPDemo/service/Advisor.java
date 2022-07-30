package com.packet.mktcenter.manage.AOPDemo.service;

import com.packet.mktcenter.manage.AOPDemo.model.Pointcut;

/**
 * 通知器，里面含有Pointcut,Advice,advisorType
 */
public interface Advisor {

    Pointcut getPointcut();

    Advice getAdvice();
    // 通知类型
    String getAdvisorType();
}
