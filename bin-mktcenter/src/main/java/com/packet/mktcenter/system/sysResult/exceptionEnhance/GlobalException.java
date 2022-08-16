package com.packet.mktcenter.system.sysResult.exceptionEnhance;

import com.packet.mktcenter.system.sysResult.model.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    /**
     * 业务异常拦截
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResultVO businessException(BusinessException e){
        ResultVO rv = new ResultVO(e.getCode(), e.getDetailMessage());
        return rv;
    }
}
