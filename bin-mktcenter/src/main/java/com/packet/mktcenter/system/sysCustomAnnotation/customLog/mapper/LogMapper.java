package com.packet.mktcenter.system.sysCustomAnnotation.customLog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.packet.mktcenter.system.sysCustomAnnotation.customLog.model.CrmLogMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper extends BaseMapper<CrmLogMessage> {
}
