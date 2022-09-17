package com.packet.mktcenter.system.sysCustomAnnotation.paramCheck.model;

/**
 * @ClassName CrmParamCheckMessage
 * @Autor lb
 * @Describe 数据库参数校验表
 */
public class CrmParamCheckMessage {
    /**id*/
    private Integer id;
    /**方法名*/
    private String methodName;
    /**参数下标集合*/
    private String paramIndex;
    /**参数集合*/
    private String Remarks;
    /**校验类型*/
    private String checkType;
    /**操作时间*/
    private String DateTime;
    /**ip地址*/
    private String ip;
    /**请求地址*/
    private String requestUrl;
    /**操作结果*/
    private String result;
    /**操作结果类型 0000表示校验成功 0001表示校验失败*/
    private String resultCode;

    public CrmParamCheckMessage(){
    }

    @Override
    public String toString() {
        return "CrmParamCheckMessage{" +
                "id=" + id +
                ", methodName='" + methodName + '\'' +
                ", paramIndex='" + paramIndex + '\'' +
                ", Remarks='" + Remarks + '\'' +
                ", checkType='" + checkType + '\'' +
                ", DateTime='" + DateTime + '\'' +
                ", ip='" + ip + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", result='" + result + '\'' +
                ", resultCode='" + resultCode + '\'' +
                '}';
    }

    public CrmParamCheckMessage(Integer id, String methodName, String paramIndex, String remarks, String checkType, String dateTime, String ip, String requestUrl, String result, String resultCode) {
        this.id = id;
        this.methodName = methodName;
        this.paramIndex = paramIndex;
        Remarks = remarks;
        this.checkType = checkType;
        DateTime = dateTime;
        this.ip = ip;
        this.requestUrl = requestUrl;
        this.result = result;
        this.resultCode = resultCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParamIndex() {
        return paramIndex;
    }

    public void setParamIndex(String paramIndex) {
        this.paramIndex = paramIndex;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
