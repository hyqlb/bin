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


package com.packet.mktcenter.manage.easyExcelDemo.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;

import java.util.Date;

/**
 * @author: libin
 * @description:用户导出信息实体类
 * @date: 2022/9/26 11:32
 * @since: 1.0.0
 */
public class UserWriteEntity {
    @ExcelProperty(value = "姓名")
    private String name;
    @ExcelProperty(value = "年龄")
    private int age;
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "操作时间")
    private Date time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}

