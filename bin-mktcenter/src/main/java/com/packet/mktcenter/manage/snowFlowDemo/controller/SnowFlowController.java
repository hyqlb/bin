package com.packet.mktcenter.manage.snowFlowDemo.controller;

import com.packet.mktcenter.manage.snowFlowDemo.model.SnowFlowInfo;
import com.packet.mktcenter.manage.snowFlowDemo.service.SnowFlowService;
import com.packet.mktcenter.manage.snowFlowDemo.service.impl.SnowFlowErrorCode;
import com.packet.mktcenter.system.sysOperateLog.model.OpType;
import com.packet.mktcenter.system.sysOperateLog.service.OpLog;
import com.packet.mktcenter.system.sysResult.exceptionEnhance.BusinessException;
import com.packet.mktcenter.system.sysResult.model.RV;
import com.packet.mktcenter.system.sysResult.model.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.Objects;
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
    //@SystemCrmlog(description = "生成了趋势递增主键", tableName = "sys_log_info")
    @OpLog(opType = OpType.INSERT, opItem = "snowFlow", opItemIdExpression = "123")
    @RequestMapping(value = {"/createSnowFlow"}, method = RequestMethod.POST)
    public ResultVO createSnowFlow(@RequestBody Map<String, String> map){
        log.info("SnowFlowController begin createSnowFlow:{}", JSON.toJSONString(map));
        SnowFlowInfo info = new SnowFlowInfo();
        if (Objects.nonNull(map.get("workerId"))){
            long workerId = Long.valueOf(map.get("workerId"));
            info.setWorkerId(workerId);
        } else {
            throw new BusinessException(SnowFlowErrorCode.SYSTEM_NULL_BUSY);
        }
        if (Objects.nonNull(map.get("datacenterId"))){
            long datacenterId = Long.valueOf(map.get("datacenterId"));
            info.setDatacenterId(datacenterId);
        } else {
            throw new BusinessException(SnowFlowErrorCode.SYSTEM_NULL_BUSY);
        }
        if (Objects.nonNull(map.get("sequence"))){
            long sequence = Long.valueOf(map.get("sequence"));
            info.setSequence(sequence);
        } else {
            throw new BusinessException(SnowFlowErrorCode.SYSTEM_NULL_BUSY);
        }
        Long snowFlowId = snowFlowService.createSnowFlow(info);
        log.info("SnowFlowController end createSnowFlow:{}", JSON.toJSONString(snowFlowId));
        return RV.success(snowFlowId);
    }
}
