package com.packet.mktcenter.system.sysResult.service;

/**
 * 错误代码的接口类
 */
public interface IErrorCode {
    /**
     * 得到错误码
     * @return
     */
    Integer getCode();

    /**
     * 得到错误信息
     * @return
     */
    String getMsg();
}
