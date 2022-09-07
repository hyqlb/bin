package com.packet.mktcenter.manage.timeWheelDemo.service.impl;

import com.packet.mktcenter.manage.timeWheelDemo.model.TimerTaskEntry;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 任务包装类
 */
@Data
@Slf4j
public class TimerTask implements Runnable {
    /**延时时间*/
    private long delayMs;
    /**任务所在的entry*/
    private TimerTaskEntry timerTaskEntry;

    private String desc;

    public TimerTask(long delayMs, String desc) {
        this.delayMs = delayMs;
        this.desc = desc;
        this.timerTaskEntry = null;
    }

    public synchronized void setTimerTaskEntry(TimerTaskEntry entry){
        /**如果这个timetask已经被一个已存在的TimerTaskEntry持有，先移除一个*/
        if (Objects.nonNull(timerTaskEntry) && timerTaskEntry != entry) {
            timerTaskEntry.remove();
        }
        timerTaskEntry = entry;
    }

    public TimerTaskEntry getTimerTaskEntry(){
        return timerTaskEntry;
    }

    @Override
    public void run(){
        log.info("==============={}任务执行", desc);
    }
}
