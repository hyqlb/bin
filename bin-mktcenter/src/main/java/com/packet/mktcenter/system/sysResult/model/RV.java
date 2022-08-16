package com.packet.mktcenter.system.sysResult.model;

import com.packet.mktcenter.system.sysResult.service.IErrorCode;
import com.packet.mktcenter.system.sysResult.service.impl.ErrorCode;

public class RV {
    /**
     * 成功的返回对象
     * @param data
     * @return ResultVO
     */
    public static ResultVO success(Object data){
        return new ResultVO(ErrorCode.SUCCESS, data);
    }

    /**
     * 失败的返回对象
     * @param errorCode
     * @return ResultVO
     */
    public static ResultVO fail(IErrorCode errorCode){
        return new ResultVO().setCodeMessage(errorCode);
    }

    /**
     * 通过errorCode和数据对象参数，构建一个新的对象
     * @param errorCode
     * @param data
     * @return ResultVO
     */
    public static ResultVO result(IErrorCode errorCode, Object data){
        return new ResultVO(errorCode, data);
    }
}
