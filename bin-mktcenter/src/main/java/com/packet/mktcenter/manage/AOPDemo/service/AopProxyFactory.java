package com.packet.mktcenter.manage.AOPDemo.service;

import com.packet.mktcenter.manage.AOPDemo.model.AdvisedSupport;

/**
 * 生成 产生代理对象的工厂的工厂
 */
public interface AopProxyFactory {
    // 根据AdvisedSupport,创建AopProxy
    AopProxy createAopProxy(AdvisedSupport advisedSupport);
}
