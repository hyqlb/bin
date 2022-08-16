package com.packet.mktcenter.system.sysResult.model;

import com.packet.mktcenter.system.sysResult.service.IErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一请求的返回对象
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> {
    /**错误代码*/
    private Integer code;
    /**消息*/
    private String msg;
    /**对应返回数据*/
    private T data;

    public ResultVO(int code, String msg) {
        setCode(code);
        setMsg(msg);
    }

    public ResultVO(IErrorCode errorCode, T data){
        setCodeMessage(errorCode);
        setData(data);
    }

    public ResultVO setCodeMessage(IErrorCode errorCode){
        setCode(errorCode.getCode());
        setMsg(errorCode.getMsg());
        return this;
    }
}
