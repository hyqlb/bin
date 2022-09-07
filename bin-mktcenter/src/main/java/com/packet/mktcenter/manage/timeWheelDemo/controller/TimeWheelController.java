package com.packet.mktcenter.manage.timeWheelDemo.controller;

import com.packet.mktcenter.manage.timeWheelDemo.service.Timer;
import com.packet.mktcenter.manage.timeWheelDemo.service.impl.TimerImpl;
import com.packet.mktcenter.manage.timeWheelDemo.service.impl.TimerTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = {"/timeWheel/timeWheelManager"})
public class TimeWheelController {

    public static void main(String[] args) {
        Timer timer = new TimerImpl();
        timer.add(new TimerTask(2L,"抽奖任务"){
            @Override
            public void run(){
                log.info("================测试任务执行===============");
            }
        });
    }

}
