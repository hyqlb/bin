package com.packet.mktcenter.manage.AOPDemo.model;

import com.packet.mktcenter.manage.AOPDemo.service.Advisor;
import com.packet.mktcenter.manage.AOPDemo.service.AdvisorAdapter;
import com.packet.mktcenter.manage.AOPDemo.service.impl.DefaultPointcutAdvisor;
import com.packet.mktcenter.manage.AOPDemo.service.Advice;
import com.packet.mktcenter.manage.AOPDemo.service.impl.MethodAdviceAdapter;
import com.packet.mktcenter.manage.AOPDemo.utils.Commutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 创建代理对象的载体，如目标对象，advice,pointcut,advisor
 */
public class AdvisedSupport {
    // 目标对象
    private Object target;
    // 代理对象实现的接口
    private List<Class<?>> interfaces = new ArrayList<>();
    private List<Advisor> advisors = new ArrayList<>();

    //适配器,advice适配成MethodInterceptor
    private AdvisorAdapter advisorAdapter = new MethodAdviceAdapter();

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    /**
     * 添加目标对象对象所实现的接口
     * @param interfaces
     */
    public void addInterface(List<Class<?>> interfaces){
        this.interfaces.clear();
        for (Class<?> ifc : interfaces){
            this.interfaces.add(ifc);
        }
    }

    public Class<?>[] getInterfaces(){
        return interfaces.toArray(new Class<?>[0]);
    }

    public void setInterfaces(List<Class<?>> interfaces){
        this.interfaces = interfaces;
    }

    public List<Advisor> getAdvisors(List<Advisor> advisors){
        return this.advisors;
    }

    public void setAdvisors(List<Advisor> advisors){
        this.advisors = advisors;
    }

    /**
     * 根据advisor，获取到advice，并将advice适配成所有的MethodInterceptor
     * @return
     */
    public List<Object> getBeforeInterceptorsAndDynamicInterceptionAdvice(){
        List<Object> interceptorList = new ArrayList<>();
        for (Advisor advisor : this.advisors){
            if (advisor instanceof DefaultPointcutAdvisor && Objects.equals(advisor.getAdvisorType(), Commutil.ADVISOR_BEFORE_TYPE)){
                interceptorList.add(advisorAdapter.getInterceptor(advisor));
            }
        }
        return interceptorList;
    }

    public List<Object> getAfterInterceptorsAndDynamicInterceptionAdvice(){
        List<Object> interceptorList = new ArrayList<>();
        for (Advisor advisor : this.advisors){
            if (advisor instanceof DefaultPointcutAdvisor && Objects.equals(advisor.getAdvisorType(), Commutil.ADVISOR_AFTER_TYPE)){
                interceptorList.add(advisorAdapter.getInterceptor(advisor));
            }
        }
        return interceptorList;
    }

    /**
     * 如果添加的是一个advice,封装成一个Advisor，并加入链表
     * @param advice
     */
    public void addAdvice(Advice advice, String advisorType){
        addAdvisor(new DefaultPointcutAdvisor(null, advice, advisorType));
    }

    public void addAdvisor(Advisor advisor){
        this.advisors.add(advisor);
    }
}
