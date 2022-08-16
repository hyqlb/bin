package com.packet.mktcenter.manage.algorithmDemo.controller;

import com.packet.mktcenter.manage.algorithmDemo.model.SnowFlowInfo;
import com.packet.mktcenter.manage.algorithmDemo.service.SnowFlowService;
import com.packet.mktcenter.manage.algorithmDemo.service.impl.SnowFlowErrorCode;
import com.packet.mktcenter.system.sysResult.exceptionEnhance.BusinessException;
import com.packet.mktcenter.system.sysResult.model.RV;
import com.packet.mktcenter.system.sysResult.model.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.Objects;

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
    @RequestMapping(value = {"/createSnowFlow"}, method = RequestMethod.POST)
    public ResultVO createSnowFlow(@RequestBody Map<String, String> map){
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
        return RV.success(snowFlowId);
    }
}
