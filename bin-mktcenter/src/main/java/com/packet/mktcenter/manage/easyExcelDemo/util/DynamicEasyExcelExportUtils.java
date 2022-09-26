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
import cn.hutool.core.map.MapUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: libin
 * @description: 动态导出工具类
 * @date: 2022/9/26 13:44
 * @since: 1.0.0
 */
@Slf4j
public class DynamicEasyExcelExportUtils {

    private static final String DEFAULT_SHEET_NAME = "sheet1";

    /**
     * 动态生成导出模板（单表头）
     * @param headColumns 列名称
     * @return excel文件流
     */
    public static byte[] exportTemplateExcelFile(List<String> headColumns){
        List<List<String>> excelHead = Lists.newArrayList();
        headColumns.forEach(columnName -> {
            excelHead.add(Lists.newArrayList(columnName));
        });
        byte[] stream = createExcelFile(excelHead, new ArrayList<>());
        return stream;
    }

    /**
     * 动态生成导出模板（多表头）
     * @param excelHead 列名称
     * @return excel文件流
     */
    public static byte[] exportTemplateExcelFileCustomHead(List<List<String>> excelHead){
        byte[] stream = createExcelFile(excelHead, new ArrayList<>());
        return stream;
    }

    /**
     * 动态导出文件（通过map方式计算）
     * @param headColumnMap 有序列头部
     * @param dataList 数据体
     * @return
     */
    public static byte[] exportExcelFile(LinkedHashMap<String, String> headColumnMap, List<Map<String, Object>> dataList){
        /**获取列名称*/
        List<List<String>> excelHead = new ArrayList<>();
        if (MapUtil.isNotEmpty(headColumnMap)){
            /**key为匹配符，value为列名，如果多级列名用逗号隔开*/
            headColumnMap.entrySet().forEach(entry -> {
                excelHead.add(Lists.newArrayList(entry.getValue().split(",")));
            });
        }
        List<List<String>> excelRows = new ArrayList<>();
        if (MapUtil.isNotEmpty(headColumnMap) && CollectionUtil.isNotEmpty(dataList)){
            for (Map<String, Object> dataMap : dataList){
                List<String> rows = new ArrayList<>();
                headColumnMap.entrySet().forEach(headColumnEntry -> {
                    if (dataMap.containsKey(headColumnEntry.getKey())){
                        String data = String.valueOf(dataMap.get(headColumnEntry.getKey()));
                        rows.add(data);
                    }
                });
                excelRows.add(rows);
            }
        }
        byte[] stream = createExcelFile(excelHead, excelRows);
        return stream;
    }

    /**
     * 生成文件（自定义头部排列）
     * @param rowHeads
     * @param excelRows
     * @return
     */
    public static byte[] customerExportExcelFile(List<List<String>> rowHeads, List<List<String>> excelRows){
        /**将行头部转成easyExcel能识别的部分*/
        List<List<String>> excelHead = transferHead(rowHeads);
        return createExcelFile(excelHead, excelRows);
    }

    /**
     * 生成文件
     * @param excelHead
     * @param excelRows
     * @return
     */
    private static byte[] createExcelFile(List<List<String>> excelHead, List<List<String>> excelRows){
        try {
            if (CollectionUtil.isNotEmpty(excelHead)){
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                EasyExcel.write(outputStream).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .head(excelHead)
                        .sheet(DEFAULT_SHEET_NAME)
                        .doWrite(excelRows);
                return outputStream.toByteArray();
            }
        } catch (Exception e){
            log.error("动态生成excel文件失败，headColumns:" + JSON.toJSONString(excelHead) + ", excelRoes:" + JSON.toJSONString(excelRows), e);
        }
        return null;
    }

    /**
     * 将行头部转成easyExcel能识别的部分
     * @param rowHeads
     * @return
     */
    public static List<List<String>> transferHead(List<List<String>> rowHeads){
        /**将头部列进行反转*/
        List<List<String>> realHead = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(rowHeads)){
            Map<Integer, List<String>> cellMap = new LinkedHashMap<>();
            /**遍历行*/
            for (List<String> cells : rowHeads){
                /**遍历列*/
                for (int i = 0; i < cells.size(); i++){
                    if (cellMap.containsKey(i)){
                        cellMap.get(i).add(cells.get(i));
                    } else {
                        cellMap.put(i, Lists.newArrayList(cells.get(i)));
                    }
                }
            }
            /**将列一行一行加入realhead*/
            cellMap.entrySet().forEach(item -> realHead.add(item.getValue()));
        }
        return realHead;
    }
}
