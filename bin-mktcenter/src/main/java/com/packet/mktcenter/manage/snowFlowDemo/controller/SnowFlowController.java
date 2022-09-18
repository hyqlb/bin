package com.packet.mktcenter.manage.snowFlowDemo.controller;

import com.packet.mktcenter.manage.snowFlowDemo.model.SnowFlowInfo;
import com.packet.mktcenter.manage.snowFlowDemo.service.SnowFlowService;
import com.packet.mktcenter.system.sysCustomAnnotation.customLog.service.SystemCrmlog;
import com.packet.mktcenter.system.sysCustomAnnotation.paramCheck.service.SysParamCheck;
import com.packet.mktcenter.system.sysResult.model.RV;
import com.packet.mktcenter.system.sysResult.model.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import com.alibaba.fastjson.JSON;


@Slf4j
@RestController
@RequestMapping(value = {"/snowFlow/SnowFlowManager"})
public class SnowFlowController {
    @Autowired
    private SnowFlowService snowFlowService;

    /**
     * 利用雪花算法生成按趋势递增的唯一主键
     * @param map
     * @return
     */
    //@SysParamCheck(paramCheckType = "1")
    @SystemCrmlog(description = "生成了趋势递增主键", tableName = "sys_snow_flow_info")
    @RequestMapping(value = {"/createSnowFlow"}, method = RequestMethod.POST)
    public ResultVO createSnowFlow(@RequestBody Map<String, String> map){
        log.info("SnowFlowController begin createSnowFlow:{}", JSON.toJSONString(map));
        SnowFlowInfo info = new SnowFlowInfo();
        Long snowFlowId = snowFlowService.createSnowFlow(info);
        log.info("SnowFlowController end createSnowFlow:{}", JSON.toJSONString(snowFlowId));
        return RV.success(snowFlowId);
    }
}
