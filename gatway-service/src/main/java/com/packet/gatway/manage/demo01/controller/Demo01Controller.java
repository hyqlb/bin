package com.packet.gatway.manage.demo01.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/demo01/demoManage"})
@RefreshScope
public class Demo01Controller {

    @Value("${nacos.test.aaa}")
    private String useLocalCache;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public String get() {
        return useLocalCache;
    }

}
