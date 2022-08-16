package com.packet.mktcenter.system.sysResult.exceptionEnhance;

import com.packet.mktcenter.system.sysResult.service.IErrorCode;
import org.apache.commons.lang.StringUtils;

/**
 * 自定义业务异常类
 */
public class BusinessException extends RuntimeException {

    private int code;
    private String detailMessage;

    public BusinessException(int code, String detailMessage) {
        super(detailMessage);
        this.code = code;
        this.detailMessage = detailMessage;
    }

    public BusinessException(IErrorCode errorCode){
        this(errorCode.getCode(), errorCode.getMsg());
    }

    public int getCode(){
        return code;
    }

    public String getDetailMessage(){
        return detailMessage;
    }
}
