package com.packet.mktcenter.manage.snowFlowDemo.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@DisplayName("SnowFlowController测试类")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(SpringRunner.class)
@Slf4j
public class SnowFlowControllerTest {
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MockMvc mvc;

    private String token;

    @Before
    public void setupMockMvc(){
        // 初始化MockMvc对象
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @BeforeEach
    void setUp() {
        System.out.println("************************" + "函数测试开始" + "***************************");
    }

    @AfterEach
    void tearDown() {
        System.out.println("************************" + "函数测试结束" + "***************************");
    }

    @Test
    @DisplayName("获取雪花算法生成趋势递增主键测试")
    @Order(1)
    public void createSnowFlow() throws Exception{
        /**模拟请求参数*/
        String jsonStr = "{\"workerId\":\"1\",\"datacenterId\":\"1\",\"sequence\":\"1\"}";
        /**模拟请求*/
        String url = "/snowFlow/SnowFlowManager/createSnowFlow";
        RequestBuilder req = MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonStr.getBytes());
        MvcResult result = mvc.perform(req).andReturn();
        /**获取请求结果*/
        result.getResponse().setCharacterEncoding("UTF-8");
        int httpStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        String message = (String) jsonObject.get("msg");
        Long data = (Long) jsonObject.get("data");

        //System.out.println("response data:" + data);
        /**请求结果断言测试*/
        /**返回码*/
        Assertions.assertTrue(httpStatus == HttpStatus.OK.value());
        /**响应内容*/
        Assertions.assertAll("组合判断",
                () -> Assertions.assertEquals("操作成功", message),
                () -> Assertions.assertNotNull(data));
    }
}
