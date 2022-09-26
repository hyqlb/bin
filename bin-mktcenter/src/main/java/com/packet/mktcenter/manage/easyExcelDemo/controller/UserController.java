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


package com.packet.mktcenter.manage.easyExcelDemo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.IoUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.packet.mktcenter.manage.easyExcelDemo.model.UserReadEntity;
import com.packet.mktcenter.manage.easyExcelDemo.model.UserWriteEntity;
import com.packet.mktcenter.manage.easyExcelDemo.util.DynamicEasyExcelExportUtils;
import com.packet.mktcenter.manage.easyExcelDemo.util.DynamicEasyExcelImportUtils;
import com.packet.mktcenter.system.sysCustomAnnotation.customLog.service.SystemCrmlog;
import com.packet.mktcenter.system.sysResult.model.RV;
import com.packet.mktcenter.system.sysResult.model.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;

/**
 * @author: libin
 * @description: 用户导入/导出测试Controller
 * @date: 2022/9/26 11:38
 * @since: 1.0.0
 */
@Slf4j
@RestController
@RequestMapping(value = {"/user/userManager"})
public class UserController {

    /**
     * 简单导出
     * @return
     * @throws FileNotFoundException
     */
    @SystemCrmlog(description = "用户导出测试方法", tableName = "")
    @RequestMapping(value = {"/userExport"}, method = RequestMethod.POST)
    public ResultVO userExport() throws FileNotFoundException {
        List<UserWriteEntity> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            UserWriteEntity userWriteEntity = new UserWriteEntity();
            userWriteEntity.setName("张三" + i);
            userWriteEntity.setAge(20 + i);
            userWriteEntity.setTime(new Date(System.currentTimeMillis() + i));
            dataList.add(userWriteEntity);
        }
        /**定义导出文件输出位置*/
        FileOutputStream outputStream = new FileOutputStream(new File("E:/soft/Ideal/user1.xlsx"));
        EasyExcel.write(outputStream, UserWriteEntity.class).sheet("用户信息").doWrite(dataList);
        return RV.success("导出成功！");
    }

    /**
     * 简单导入
     * @return
     * @throws FileNotFoundException
     */
    @SystemCrmlog(description = "用户导入测试方法", tableName = "")
    @RequestMapping(value = {"/userImport"}, method = RequestMethod.POST)
    public ResultVO userImport() throws FileNotFoundException {
        /**同步读取文件内容*/
        FileInputStream inputStream = new FileInputStream(new File("E:/soft/Ideal/user1.xlsx"));
        List<UserReadEntity> list = EasyExcel.read(inputStream).head(UserReadEntity.class).sheet().doReadSync();
        log.info(JSON.toJSONString(list));
        /**导入逻辑*/

        return RV.success("导入成功！");
    }

    /**
     * 动态自由导出一
     * @return
     * @throws IOException
     */
    @SystemCrmlog(description = "用户动态自由导出测试方法一", tableName = "")
    @RequestMapping(value = {"/exportUserFileOne"}, method = RequestMethod.POST)
    public ResultVO exportUserFileOne() throws IOException {
        /**导出包含数据内容的文件*/
        LinkedHashMap<String, String> headColumnMap = Maps.newLinkedHashMap();
        headColumnMap.put("className", "班级");
        headColumnMap.put("name", "学生信息，姓名");
        headColumnMap.put("sex", "学生信息，性别");
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            Map<String, Object> dataMap = Maps.newHashMap();
            dataMap.put("className", "一年级");
            dataMap.put("name", "张三" + i);
            dataMap.put("sex", "男");
            dataList.add(dataMap);
        }
        byte[] stream = DynamicEasyExcelExportUtils.exportExcelFile(headColumnMap, dataList);
        FileOutputStream outputStream = new FileOutputStream(new File("E:/soft/Ideal/user1.xlsx"));
        outputStream.write(stream);
        outputStream.close();
        return RV.success("导出成功！");
    }

    /**
     * 动态自由导出二
     * @return
     * @throws IOException
     */
    @SystemCrmlog(description = "用户动态自由导出测试方法二", tableName = "")
    @RequestMapping(value = {"/exportUserFileSecond"}, method = RequestMethod.POST)
    public ResultVO exportUserFileSecond() throws IOException {
        /**导出包含数据内容的文件*/
        /**头部 第一层*/
        List<String> head1 = new ArrayList<>();
        head1.add("第一行头部列1");
        head1.add("第一行头部列2");
        head1.add("第一行头部列3");
        head1.add("第一行头部列4");
        /**头部 第二层*/
        List<String> head2 = new ArrayList<>();
        head2.add("第二行头部列1");
        head2.add("第二行头部列2");
        head2.add("第二行头部列3");
        head2.add("第二行头部列4");
        /**头部 第三层*/
        List<String> head3 = new ArrayList<>();
        head3.add("第三行头部列1");
        head3.add("第三行头部列2");
        head3.add("第三行头部列3");
        head3.add("第三行头部列4");
        /**封装头部*/
        List<List<String>> allhead = new ArrayList<>();
        allhead.add(head1);
        allhead.add(head2);
        allhead.add(head3);
        /**封装数据体*/
        /**第一行*/
        List<String> data1 = Lists.newArrayList("001","002","003","004");
        /**第二行*/
        List<String> data2 = Lists.newArrayList("0001","0002","0003","0004");
        List<List<String>> allData = Lists.newArrayList(data1,data2);

        byte[] stream = DynamicEasyExcelExportUtils.customerExportExcelFile(allhead, allData);
        FileOutputStream outputStream = new FileOutputStream(new File("E:/soft/Ideal/user1.xlsx"));
        outputStream.write(stream);
        outputStream.close();
        return RV.success("导出成功！");
    }

    /**
     * 动态自由导入
     * @return
     * @throws IOException
     */
    @SystemCrmlog(description = "用户动态自由导入测试方法", tableName = "")
    @RequestMapping(value = {"/userImportFile"}, method = RequestMethod.POST)
    public ResultVO userImportFile() throws IOException {
        FileInputStream inputStream = new FileInputStream(new File("E:/soft/Ideal/user1.xlsx"));
        byte[] stream = IoUtils.toByteArray(inputStream);
        List<Map<String, String>> dataList = DynamicEasyExcelImportUtils.parseExcelToView(stream, 2);
        log.info(JSON.toJSONString(dataList));
        inputStream.close();
        return RV.success("导入成功！");
    }

}
