package com.packet.mktcenter.manage.snowFlowDemo.controller;

import com.packet.mktcenter.manage.snowFlowDemo.model.SnowFlowInfo;
import com.packet.mktcenter.manage.snowFlowDemo.service.SnowFlowService;
import com.packet.mktcenter.system.sysCustomAnnotation.customLog.service.SystemCrmlog;
import com.packet.mktcenter.system.sysCustomAnnotation.paramCheck.service.SysParamCheck;
import com.packet.mktcenter.system.sysResult.model.RV;
import com.packet.mktcenter.system.sysResult.model.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import com.alibaba.fastjson.JSON;

@Api("雪花算法管理相关接口")
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
    @ApiOperation("生成了趋势递增主键")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workerId", value = "工作机器ID", required = true), //required为是否必填项
            @ApiImplicitParam(name = "datacenterId", value = "数据中心ID", required = true),
            @ApiImplicitParam(name = "sequence", value = "代表一毫秒内生成的多个id的最新序号", required = true)
    })
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
