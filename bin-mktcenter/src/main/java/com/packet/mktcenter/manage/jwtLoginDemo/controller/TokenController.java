package com.packet.mktcenter.manage.jwtLoginDemo.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.packet.mktcenter.manage.jwtLoginDemo.config.JwtConfig;
import com.packet.mktcenter.system.sysResult.model.RV;
import com.packet.mktcenter.system.sysResult.model.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = {"/token/tokenManager"})
public class TokenController {
    @Resource
    private JwtConfig jwtConfig;

    /**
     * 用户登录接口
     * @param map
     * @return
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public ResultVO login(@RequestBody Map<String, String> map){
        JSONObject json = new JSONObject();
        /** 验证userName，passWord和数据库中是否一致，如不一致，直接返回登陆失败 【这里省略该步骤】*/
        /**这里模拟通过用户名和密码，从数据库查询userId*/
        String userId = 5 + "";
        String token = jwtConfig.createToken(userId) ;
        if (StrUtil.isNotBlank(token)) {
            json.put("token", token);
        }
        return RV.success(json);
    }

    /**
     * 需要 Token 验证的接口
     */
    @RequestMapping("/info")
    public ResultVO<?> info (){
        return RV.success("info") ;
    }

    /**
     * 根据请求头的token获取userId
     * @param request
     * @return
     */
    @RequestMapping("/getUserInfo")
    public ResultVO<?> getUserInfo(HttpServletRequest request){
        String usernameFromToken = jwtConfig.getUsernameFromToken(request.getHeader("token"));
        return RV.success(usernameFromToken) ;
    }

}
