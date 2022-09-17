package com.packet.mktcenter.system.sysCustomAnnotation.paramCheck.model;

import com.alibaba.fastjson.JSON;
import com.packet.mktcenter.system.sysCustomAnnotation.paramCheck.service.SysParamCheck;
import com.packet.mktcenter.system.sysResult.model.RV;
import com.packet.mktcenter.system.sysResult.model.ResultVO;
import com.packet.mktcenter.system.sysResult.service.IErrorCode;
import com.packet.mktcenter.system.sysResult.service.impl.ErrorCode;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName SystemParamCheckAspect
 * @Autor lb
 * @Describe 定义切入面类
 */
@Aspect
@Component
public class SystemParamCheckAspect {

    /**
     * 注解Pointcut切入点
     * 定义出一个或一组方法，当执行这些方法时可产生通知
     * 指向你的切面类方法
     * 由于这里使用了自定义注解所以指向你的自定义注解
     */
    @Pointcut("@annotation(com.packet.mktcenter.system.sysCustomAnnotation.paramCheck.service.SysParamCheck)")
    public void facade(){
    }

    @Before(value = "facade()")
    public void doParamCheck(JoinPoint joinPoint) throws Exception{
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        CrmParamCheckMessage crmParamCheckMessage = new CrmParamCheckMessage();
        /**校验参数的方法名*/
        crmParamCheckMessage.setMethodName(getServiceMethodName(joinPoint));
        /**校验参数的下标*/
        crmParamCheckMessage.setParamIndex(getServiceParamCheckIndex(joinPoint));
        /**校验参数的集合*/
        crmParamCheckMessage.setRemarks(getServiceRemarks(joinPoint, crmParamCheckMessage.getParamIndex()));
        /**校验参数的校验类型*/
        crmParamCheckMessage.setCheckType(getServiceCheckType(joinPoint));
        /**操作时间*/
        SimpleDateFormat sif = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        crmParamCheckMessage.setDateTime(sif.format(new Date()));
        /**ip地址*/
        crmParamCheckMessage.setIp(httpServletRequest.getRemoteAddr());
        /**请求地址*/
        crmParamCheckMessage.setRequestUrl(httpServletRequest.getRequestURI());
        Map<String, String> map = checkParam(joinPoint, crmParamCheckMessage.getRemarks(), crmParamCheckMessage.getCheckType(), crmParamCheckMessage.getParamIndex());
        /**操作结果*/
        String result = String.valueOf(map.get("result"));
        crmParamCheckMessage.setResult(result);
        /**操作结果类型*/
        String resultCode = String.valueOf(map.get("resultCode"));
        crmParamCheckMessage.setResultCode(resultCode);
        System.out.println("校验返回后输出的crmParamCheckMessage日志：" + JSON.toJSONString(crmParamCheckMessage));
        /**将数据保存到数据库或缓存*/

        /*if (Objects.equals(crmParamCheckMessage.getResultCode(), "0000")){
            System.out.println("校验成功");
        }
        if (Objects.equals(crmParamCheckMessage.getResultCode(), "0001")){
            System.out.println("校验失败");
            return RV.fail(ErrorCode.SYSTEM_PARAMS_FAIL);
        }
        return RV.success(ErrorCode.SUCCESS);*/
    }

    /**
     *
     * @param joinPoint
     * @param remarks 校验参数
     * @param checkType 校验参数类型
     * @param paramIndex 校验参数下标
     * @return
     */
    private Map<String, String> checkParam(JoinPoint joinPoint, String remarks, String checkType, String paramIndex) {
        Map<String, String> map = new HashMap<String, String>();
        String result = "";
        String resultCode = "";
        /**类名*/
        String targetName = joinPoint.getTarget().getClass().getName();
        /**方法名*/
        String methodName = joinPoint.getSignature().getName();
        /**校验参数是否为空*/
        if (Objects.equals(checkType, "0")){
            if (Objects.isNull(remarks) || remarks.length() == 0){
                result = targetName + "类中" + methodName + "方法的第" + paramIndex + "个入参参数不为空校验失败，请检查该入参参数";
                resultCode = "0001";
            } else {
                String[] params = remarks.split(",");
                if (params.length == 1){
                    result = targetName + "类中" + methodName + "方法的第" + paramIndex + "个入参参数不为空校验成功";
                    resultCode = "0000";
                }
                if (params.length > 1){
                    String successIndex = "";
                    String failIndex = "";
                    for (int i = 0; i < params.length; i++){
                        if (Objects.nonNull(params[i]) && params[i].length() > 0){
                            successIndex = successIndex + String.valueOf(i) + ",";
                        } else {
                            failIndex = failIndex + String.valueOf(i) + ",";
                        }
                    }
                    /**全部参数校验失败*/
                    if (Objects.isNull(successIndex) || successIndex.length() == 0){
                        result = targetName + "类中" + methodName + "方法的所有入参参数不为空校验失败，请检查所有入参参数";
                        resultCode = "0001";
                    }
                    /**全部参数校验成功*/
                    if (Objects.isNull(failIndex) || failIndex.length() == 0){
                        result = targetName + "类中" + methodName + "方法的所有入参参数不为空校验成功";
                        resultCode = "0000";
                    }
                    /**部分参数校验成功，部分参数校验失败*/
                    if (Objects.nonNull(successIndex) && Objects.nonNull(failIndex) && successIndex != "" && failIndex != ""){
                        successIndex = successIndex.substring(0, successIndex.length()-1);
                        failIndex = failIndex.substring(0, failIndex.length()-1);
                        result = targetName + "类中" + methodName + "方法的第" + successIndex + "个入参参数不为空校验成功;第" + failIndex +"个入参参数不为空校验失败，请检查该入参参数";
                        resultCode = "0001";
                    }
                }
            }
        }
        /**校验参数是否为数字*/
        if (Objects.equals(checkType, "1")){
            if (Objects.isNull(remarks) || remarks.length() == 0){
                result = targetName + "类中" + methodName + "方法的第" + paramIndex + "个入参参数是否为数字校验失败，请检查该入参参数";
                resultCode = "0001";
            } else {
                String[] params = remarks.split(",");
                if (params.length == 1){
                    if (StringUtils.isNumeric(remarks)){
                        result = targetName + "类中" + methodName + "方法的第" + paramIndex + "个入参参数是否为数字校验成功";
                        resultCode = "0000";
                    } else {
                        result = targetName + "类中" + methodName + "方法的第" + paramIndex + "个入参参数是否为数字校验失败，请检查该入参参数";
                        resultCode = "0001";
                    }
                }
                if (params.length > 1){
                    String successIndex = "";
                    String failIndex = "";
                    for (int i = 0; i < params.length; i++){
                        if (StringUtils.isNumeric(params[i])){
                            successIndex = successIndex + String.valueOf(i) + ",";
                        } else {
                            failIndex = failIndex + String.valueOf(i) + ",";
                        }
                    }
                    /**全部参数校验失败*/
                    if (Objects.isNull(successIndex) || successIndex.length() == 0){
                        result = targetName + "类中" + methodName + "方法的所有入参参数是否为数字校验失败，请检查所有入参参数";
                        resultCode = "0001";
                    }
                    /**全部参数校验成功*/
                    if (Objects.isNull(failIndex) || failIndex.length() == 0){
                        result = targetName + "类中" + methodName + "方法的所有入参参数是否为数字校验成功";
                        resultCode = "0000";
                    }
                    /**部分参数校验成功，部分参数校验失败*/
                    if (Objects.nonNull(successIndex) && Objects.nonNull(failIndex) && successIndex.length() != 0 && failIndex.length() != 0){
                        successIndex = successIndex.substring(0, successIndex.length()-1);
                        failIndex = failIndex.substring(0, failIndex.length()-1);
                        result = targetName + "类中" + methodName + "方法的第" + successIndex + "个入参参数是否为数字校验成功;第" + failIndex +"个入参参数是否为数字校验失败，请检查该入参参数";
                        resultCode = "0001";
                    }
                }
            }
        }
        map.put("result", result);
        map.put("resultCode", resultCode);
        return map;
    }

    /**
     * 获取自定义注解里的校验参数类型
     * @param joinPoint
     * @return
     */
    private String getServiceCheckType(JoinPoint joinPoint) throws Exception {
        String paramCheckType = "";
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
        for (Method method : methods){
            /**判断方法名是否一致*/
            if (method.getName().equals(methodName)){
                /**比较参数数组的长度*/
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length){
                    /**获取注解里的日志对象*/
                    paramCheckType = method.getAnnotation(SysParamCheck.class).paramCheckType();
                    break;
                }
            }
        }
        return paramCheckType;
    }

    /**
     * 获取自定义注解里的校验参数集合
     * @param joinPoint
     * @param paramIndex 校验参数下标
     * @return
     */
    private String getServiceRemarks(JoinPoint joinPoint, String paramIndex) {
        String message = "";
        /**参数*/
        Object[] arguments = joinPoint.getArgs();
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> map = (Map<String, String>) arguments[0];
        for (Map.Entry<String,String> entry:map.entrySet()){
            Map<String,String> newMap = new HashMap<>();
            newMap.put(entry.getKey(), entry.getValue());
            list.add(newMap);
        }
        /**
         * 参数下标不为空
         * 取参数下标所对应的参数值
         */
        if (Objects.nonNull(paramIndex) && paramIndex.length() != 0){
            Map<String,String> paramMap = list.get(Integer.valueOf(paramIndex));
            for (Map.Entry<String,String> entry:paramMap.entrySet()){
                message = String.valueOf(entry.getValue());
            }
        }
        /**
         * 参数下标为空
         * 取所有参数值
         */
        if (Objects.isNull(paramIndex) || paramIndex.length() == 0){
            for (int i = 0; i < list.size(); i++){
                Map<String,String> paramMap = list.get(i);
                for (Map.Entry<String,String> entry:paramMap.entrySet()){
                    message = message + String.valueOf(entry.getValue()) + ",";
                }
            }
            message = message.substring(0, message.length()-1);
        }
        return message;
    }

    /**
     * 获取自定义注解里的校验参数下标
     * @param joinPoint
     * @return
     */
    private String getServiceParamCheckIndex(JoinPoint joinPoint) throws Exception {
        String paramCheckIndex = "";
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
        for (Method method : methods){
            /**判断方法名是否一致*/
            if (method.getName().equals(methodName)){
                /**比较参数数组的长度*/
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length){
                    /**获取注解里的日志对象*/
                    paramCheckIndex = method.getAnnotation(SysParamCheck.class).paramIndex();
                    break;
                }
            }
        }
        return paramCheckIndex;
    }

    /**
     * 获取自定义注解里的方法名
     * @param joinPoint
     * @return
     */
    private String getServiceMethodName(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        return methodName;
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
