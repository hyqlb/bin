package com.packet.mktcenter.manage.timeDemo.controller;

import com.packet.mktcenter.manage.timeDemo.model.ITask;
import com.packet.mktcenter.manage.timeDemo.service.IWheel;
import com.packet.mktcenter.manage.timeDemo.service.impl.TimerWheel;

import java.util.Random;

public class DemoController {

    public static void main(String[] args) {
        Random  random = new Random();
        IWheel iWheel = new TimerWheel(1,60,5);
        String taskId=iWheel.addTask(new ITask() {
            @Override
            public void run() {
                System.out.println("测试任务执行");
            }
        },random.nextInt(10)+10);
        System.out.println("------taskId---------" + taskId);
    }

}
