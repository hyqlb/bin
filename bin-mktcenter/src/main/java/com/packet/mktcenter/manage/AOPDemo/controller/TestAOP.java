package com.packet.mktcenter.manage.AOPDemo.controller;
import com.packet.mktcenter.manage.AOPDemo.service.Advisor;
import com.packet.mktcenter.manage.AOPDemo.service.*;
import com.packet.mktcenter.manage.AOPDemo.service.impl.ADemoImpl;
import com.packet.mktcenter.manage.AOPDemo.model.AdvisedSupport;
import com.packet.mktcenter.manage.AOPDemo.service.impl.DefaultAopproxyFactory;
import com.packet.mktcenter.manage.AOPDemo.service.impl.DefaultPointcutAdvisor;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestAOP {
    public static void main(String[] args) {
        DefaultAopproxyFactory factory = new DefaultAopproxyFactory();
        ADemo ia = new ADemoImpl();
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTarget(ia);
        Advice advice1 = new MethodAdvice() {
            @Override
            public void sendMsg(Method method, Object[] args, Object target) {
                System.out.println("1----------------");
            }
        };
        Advice advice2 = new MethodAdvice() {
            @Override
            public void sendMsg(Method method, Object[] args, Object target) {
                System.out.println("2----------------");
            }
        };
        List<Advisor> advisors = new ArrayList<>();
        Advisor advisor1 = new DefaultPointcutAdvisor(null, advice1, "0");
        Advisor advisor2 = new DefaultPointcutAdvisor(null, advice2, "1");
        advisors.add(advisor1);
        advisors.add(advisor2);
        advisedSupport.setAdvisors(advisors);
        // advisedSupport.addAdvice(advice);
        List<Class<?>> interfaces = new ArrayList<>();
        interfaces.add(ADemo.class);
        advisedSupport.addInterface(interfaces);
        AopProxy aopProxy = factory.createAopProxy(advisedSupport);
        ADemo proxyObj = (ADemo) aopProxy.getProxy();
        proxyObj.send();
    }
}

