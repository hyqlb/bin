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


package com.packet.mktcenter.manage.easyExcelDemo.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.packet.mktcenter.manage.easyExcelDemo.listener.DynaminEasyExcelListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: libin
 * @description: 动态导入工具类
 * @date: 2022/9/26 17:27
 * @since: 1.0.0
 */
@Slf4j
public class DynamicEasyExcelImportUtils {

    /**
     * 动态获取全部列和数据体，默认从第一行开始解析数据
     * @param stream
     * @return
     */
    public static List<Map<String, String>> parseExcelToView(byte[] stream){
        return parseExcelToView(stream, 1);
    }

    /**
     * 动态获取全部列和数据体
     * @param stream excel文件流
     * @param parseRowNumber 指定读取行
     * @return
     */
    public static List<Map<String, String>> parseExcelToView(byte[] stream, Integer parseRowNumber){
        DynaminEasyExcelListener readListener = new DynaminEasyExcelListener();
        EasyExcelFactory.read(new ByteArrayInputStream(stream))
                .registerReadListener(readListener)
                .headRowNumber(parseRowNumber)
                .sheet(0)
                .doRead();
        List<Map<Integer, String>> headList = readListener.getHeadList();
        if (CollectionUtil.isEmpty(headList)){
            throw new RuntimeException("Excel未包含表头");
        }
        List<Map<Integer, String>> dataList = readListener.getDataList();
        if (CollectionUtil.isEmpty(dataList)){
            throw new RuntimeException("Excel未包含数据");
        }
        /**获取头部，取最后一次解析的列头数据*/
        Map<Integer, String> excelHeadIdxNameMap = headList.get(headList.size() -1);
        /**封装数据体*/
        List<Map<String, String>> excelDataList = Lists.newArrayList();
        for (Map<Integer, String> dataRow : dataList){
            Map<String, String> rowData = new LinkedHashMap<>();
            excelHeadIdxNameMap.entrySet().forEach(columnHead -> {
                rowData.put(columnHead.getValue(), dataRow.get(columnHead.getKey()));
            });
            excelDataList.add(rowData);
        }
        return excelDataList;
    }
}
