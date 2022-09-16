package com.packet.mktcenter.system.sysCustomAnnotation.customLog.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName WebUtil
 * @Autor lb
 * @Describe 日志帮助类，用来获取session中的用户信息来存入数据库
 */
public class WebUtil {
    /**
     * 从session中获取到用户对象
     * @param request
     * @return
     */
    public Map<String, Object> getUser(HttpServletRequest request){
        Map<String, Object> attribute = null;
        if (Objects.nonNull(request)){
            Object user = request.getSession().getAttribute("USER_KEY");
            attribute = (Map<String, Object>)user;
        }
        return attribute;
    }
}
