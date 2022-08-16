package com.packet.mktcenter.system.sysResult.service.impl;

import com.packet.mktcenter.system.sysResult.service.IErrorCode;

public enum ErrorCode implements IErrorCode {
    /**
     * 1.以下错误码的定义，需要提前与前端商议
     * 2.错误码按模块进行错误码规划
     * 3.所有错误码枚举类均需要实现错误码接口类
     */
    SUCCESS(0, "操作成功"),
    SYSTEM_BUSY(10000, "系统繁忙，请稍后再试！"),
    ERROR(1, "操作失败");

    private Integer code;
    private String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
