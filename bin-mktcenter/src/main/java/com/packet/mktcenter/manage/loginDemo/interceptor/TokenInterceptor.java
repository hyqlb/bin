package com.packet.mktcenter.manage.loginDemo.interceptor;

import cn.hutool.core.util.StrUtil;
import com.packet.mktcenter.manage.loginDemo.config.JwtConfig;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SignatureException;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws SignatureException {
        /**地址过滤*/
        String uri = request.getRequestURI();
        if (uri.contains("/login")){
            return true;
        }
        /**Token验证*/
        String token = request.getHeader(jwtConfig.getHeader());
        if (StrUtil.isBlank(token)){
            token = request.getParameter(jwtConfig.getHeader());
        }

        if (StrUtil.isBlank(token)){
            throw new SignatureException(jwtConfig.getHeader() + "不能为空");
        }

        Claims claims = null;
        try{
            claims = jwtConfig.getTokenClaim(token);
            if (claims == null || jwtConfig.isTokenExpired(claims.getExpiration())){
                throw new SignatureException(jwtConfig.getHeader() + "失效，请重新登录。");
            }
        } catch (Exception e){
            throw new SignatureException(jwtConfig.getHeader() + "失效，请重新登录。");
        }

        request.setAttribute("identityId", claims.getSubject());
        return true;
    }
}
