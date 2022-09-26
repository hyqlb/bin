/*****************************************************************************
 *
 *  WIZ HOLDINGS HIGHLY CONFIDENTIAL INFORMATION:
 *
 *  THIS SOFTWARE CONTAINS CONFIDENTIAL INFORMATION AND TRADE  SECRETS  OF
 *  WIZ HOLDINGS PTE. LTD. AND MAY BE PROTECTED BY ONE OR MORE  PATENTS.
 *  USE, DISCLOSURE, OR REPRODUCTION OF ANY PORTION  OF THIS  SOFTWARE  IS
 *  PROHIBITED WITHOUT THE PRIOR EXPRESS WRITTEN PERMISSION OF WIZ HOLDINGS
 *  PTE. LTD.
 *
 *  Copyright  2022 - 2022 WIZ Holdings PTE. LTD.  All rights reserved  as an
 *  unpublished work.
 *
 *****************************************************************************/


package com.packet.mktcenter.manage.easyExcelDemo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: libin
 * @description: 文件读取监听器
 * @date: 2022/9/26 17:06
 * @since: 1.0.0
 */
@Slf4j
public class DynaminEasyExcelListener extends AnalysisEventListener<Map<Integer, String>> {

    /**表头数据（存储所有的表头数据）*/
    private List<Map<Integer, String>> headList = new ArrayList<>();
    /**数据体*/
    private List<Map<Integer, String>> dataList = new ArrayList<>();

    /**
     * 这个方法是一行行的返回头部信息
     * @param headMap
     * @param analysisContext
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext analysisContext){
        log.info("解析到一条头数据：{}", JSON.toJSONString(headMap));
        /**存储全部表头数据*/
        headList.add(headMap);
    }

    /**
     * 这个方法每一条数据解析都会被调用
     * @param data
     * @param analysisContext
     */
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext analysisContext) {
        log.info("解析到一条数据：{}", JSON.toJSONString(data));
        /**存储全部表头数据*/
        headList.add(data);
    }

    /**
     * 这个方法在所有数据都解析完成后调用
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        /**这里也要保存数据，确保最后遗留的数据也存储到数据库*/
        log.info("所有解析完成！");
    }

    public List<Map<Integer, String>> getHeadList() {
        return headList;
    }

    public List<Map<Integer, String>> getDataList() {
        return dataList;
    }
}
