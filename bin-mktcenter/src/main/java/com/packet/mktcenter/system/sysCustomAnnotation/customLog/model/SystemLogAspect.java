package com.packet.mktcenter.system.sysCustomAnnotation.customLog.model;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packet.mktcenter.system.sysCustomAnnotation.customLog.mapper.LogMapper;
import com.packet.mktcenter.system.sysCustomAnnotation.customLog.service.SystemCrmlog;
import com.packet.mktcenter.system.sysCustomAnnotation.customLog.util.WebUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName SystemLogAspect
 * @Autor lb
 * @Describe 定义切入面类
 */
@Aspect
@Component
public class SystemLogAspect {

    @Autowired
    private LogMapper logMapper;
    /**
     * 注解Pointcut切入点
     * 定义出一个或一组方法，当执行这些方法时可产生通知
     * 指向你的切面类方法
     * 由于这里使用了自定义注解所以指向你的自定义注解
     */
    @Pointcut("@annotation(com.packet.mktcenter.system.sysCustomAnnotation.customLog.service.SystemCrmlog)")
    public void crmAspect(){
    }

    /**
     * 抛出异常后通知（@AfterThrowing）：方法抛出异常退出时执行的通知
     * 注意在这里不能使用ProceedingJoinPoint
     * 不然会报错ProceedingJoinPoint is only supported for around advice
     * throwing注解为错误信息
     * @param joinPoint
     * @param ex
     * @throws Exception
     */
    @AfterThrowing(value="crmAspect()", throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception ex) throws Exception{
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        /**获取管理员用户信息*/
        WebUtil webUtil = new WebUtil();
        Map<String, Object> user = webUtil.getUser(httpServletRequest);
        CrmLogMessage log = new CrmLogMessage();
        /**获取需要的信息*/
        String context = getServiceMethodDescirption(joinPoint);
        String usr_name = "";
        String rolename = "";
        if (Objects.nonNull(user)){
            usr_name = String.valueOf(user.get("usr_name"));
            rolename = String.valueOf(user.get("rolename"));
        }
        /**管理员姓名*/
        log.setUserName(usr_name);
        /**角色名*/
        log.setUserRole(rolename);
        /**日志信息*/
        log.setContent(usr_name+context);
        /**设置参数集合*/
        log.setRemarks(getServiceMethodParams(joinPoint));
        /**设置表名*/
        log.setTableName(getServiceMethodTableName(joinPoint));
        /**操作时间*/
        SimpleDateFormat sif = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.setDateTime(sif.format(new Date()));
        /**设置ip地址*/
        log.setIp(httpServletRequest.getRemoteAddr());
        /**设置请求地址*/
        log.setRequestUrl(httpServletRequest.getRequestURI());
        /**执行结果*/
        log.setResult("执行失败");
        /**错误信息*/
        log.setExString(ex.getMessage());

        System.out.println("异常后输出的log日志：" + JSON.toJSONString(log));
        /**将数据保存到数据库*/
        int num = logMapper.insert(log);
        System.out.println("num....................." + num);
    }

    /**
     * 返回后通知（@AfterReturning）：在某连接点（joinpoint）
     * 正常完成后执行的通知：例如，一个方法没有抛出任何异常，正常返回
     * 方法执行完毕之后
     * 注意在这里不能使用ProceedingJoinPoint
     * 不然会报错ProceedingJoinPoint is only supported for around advice
     * crmAspect()指向需要控制的方法
     * returning 注解返回值
     * @param joinPoint
     * @param returnValue
     * @throws Exception
     */
    @AfterReturning(value="crmAspect()", returning = "returnValue")
    public void doCrmLog(JoinPoint joinPoint, Object returnValue) throws Exception{
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        /**获取管理员用户信息*/
        WebUtil webUtil = new WebUtil();
        Map<String, Object> user = webUtil.getUser(httpServletRequest);
        CrmLogMessage log = new CrmLogMessage();
        String context = getServiceMethodDescirption(joinPoint);
        String usr_name = "";
        String rolename = "";
        if (Objects.nonNull(user)){
            usr_name = String.valueOf(user.get("usr_name"));
            rolename = String.valueOf(user.get("rolename"));
        }
        /**管理员姓名*/
        log.setUserName(usr_name);
        /**角色名*/
        log.setUserRole(rolename);
        /**日志信息*/
        log.setContent(usr_name+context);
        /**设置参数集合*/
        log.setRemarks(getServiceMethodParams(joinPoint));
        /**设置表名*/
        log.setTableName(getServiceMethodTableName(joinPoint));
        /**操作时间*/
        SimpleDateFormat sif = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.setDateTime(sif.format(new Date()));
        /**设置ip地址*/
        log.setIp(httpServletRequest.getRemoteAddr());
        /**设置请求地址*/
        log.setRequestUrl(httpServletRequest.getRequestURI());
        /**设置请求地址*/
        if (Objects.nonNull(returnValue)){
            if (returnValue instanceof List){
                List ls = (List)returnValue;
                if (ls.size() > 0){
                    log.setResult("执行成功");
                } else {
                    log.setResult("执行失败");
                }
            } else if (returnValue instanceof Boolean){
                Boolean flag = (Boolean)returnValue;
                if (flag){
                    log.setResult("执行成功");
                } else {
                    log.setResult("执行失败");
                }
            } else if (returnValue instanceof Integer){
                Integer i = (Integer)returnValue;
                if (i > 0){
                    log.setResult("执行成功");
                } else {
                    log.setResult("执行失败");
                }
            } else {
                log.setResult("执行成功");
            }
        }

        System.out.println("正常返回后输出的log日志：" + JSON.toJSONString(log));
        /**将数据保存到数据库*/
        int num = logMapper.insert(log);
        System.out.println("num....................." + num);
    }

    /**
     * 获取自定义注解里的表名
     * @param joinPoint
     * @return 返回注解里的表名称
     * @throws Exception
     */
    private String getServiceMethodTableName(JoinPoint joinPoint) throws Exception{
        /**类名*/
        String targetName = joinPoint.getTarget().getClass().getName();
        /**方法名*/
        String methodName = joinPoint.getSignature().getName();
        /**参数*/
        Object[] arguments = joinPoint.getArgs();
        /**通过反射获取实例对象*/
        Class targetClass = Class.forName(targetName);
        /**通过实例对象获取方法数组*/
        Method[] methods = targetClass.getMethods();
        /**表名*/
        String tableName = "";
        for (Method method : methods){
            /**判断方法名是否一致*/
            if (method.getName().equals(methodName)){
                /**比较参数数组的长度*/
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length){
                    /**获取注解里的日志对象*/
                    tableName = method.getAnnotation(SystemCrmlog.class).tableName();
                    break;
                }
            }
        }
        return tableName;
    }

    /**
     * 获取json格式的参数用于存储到数据库中
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private String getServiceMethodParams(JoinPoint joinPoint) throws Exception{
        Object[] arguments = joinPoint.getArgs();
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(arguments);
    }

    /**
     * 获取自定义注解里的日志描述
     * @param joinPoint
     * @return 返回注解里面的日志描述
     * @throws Exception
     */
    private String getServiceMethodDescirption(JoinPoint joinPoint) throws Exception{
        /**类名*/
        String targetName = joinPoint.getTarget().getClass().getName();
        /**方法名*/
        String methodName = joinPoint.getSignature().getName();
        /**参数*/
        Object[] arguments = joinPoint.getArgs();
        /**通过反射获取实例对象*/
        Class targetClass = Class.forName(targetName);
        /**通过实例对象获取方法数组*/
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods){
            /**判断方法名是否一致*/
            if (method.getName().equals(methodName)){
                /**比较参数数组的长度*/
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length){
                    /**获取注解里的日志对象*/
                    description = method.getAnnotation(SystemCrmlog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 获取当前的request
     * 这里如果报空指针异常是因为单独使用spring获取request
     * 需要在配置文件里添加监听
     * <listener>
     *     <listener-class>
     *         org.springframework.web.context.request.RequestContextListener
     *     </listener-class>
     * </listener>
     * @return
     */
    public HttpServletRequest getHttpServletRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }
}
