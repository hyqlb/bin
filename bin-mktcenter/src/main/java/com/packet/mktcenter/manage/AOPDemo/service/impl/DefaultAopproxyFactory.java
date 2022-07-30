package com.packet.mktcenter.manage.AOPDemo.service.impl;

import com.packet.mktcenter.manage.AOPDemo.model.AdvisedSupport;
import com.packet.mktcenter.manage.AOPDemo.service.AopProxy;
import com.packet.mktcenter.manage.AOPDemo.service.AopProxyFactory;

public class DefaultAopproxyFactory implements AopProxyFactory {

    @Override
    public AopProxy createAopProxy(AdvisedSupport advisedSupport) {
        Object target = advisedSupport.getTarget();
        if (target.getClass().getInterfaces().length > 0) {
            return new JdkDynamicAopProxy(advisedSupport);
        } else {
            // cglib
            return null;
        }
    }
}
