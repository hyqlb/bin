package com.packet.mktcenter.manage.timeWheelDemo.service.impl;

import com.packet.mktcenter.manage.timeWheelDemo.model.TimeWheelInfo;
import com.packet.mktcenter.manage.timeWheelDemo.model.TimerTaskEntry;
import com.packet.mktcenter.manage.timeWheelDemo.model.TimerTaskList;
import com.packet.mktcenter.manage.timeWheelDemo.service.Timer;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import com.packet.mktcenter.manage.timeWheelDemo.service.impl.TimerTask;
import org.springframework.stereotype.Service;

/**
 * 定时器实现类
 */
@Slf4j
@Service
public class TimerImpl implements Timer {
    /**底层时间轮*/
    private TimeWheelInfo info;
    /**一个Timer只有一个延时队列*/
    private DelayQueue<TimerTaskList> delayQueue = new DelayQueue<>();
    /**过期任务执行线程*/
    private ExecutorService workerThreadPool;
    /**轮询delayQueue获取过期任务线程*/
    private ExecutorService bossThreadPool;

    public TimerImpl() {
        this.info = new TimeWheelInfo(1, 100, System.currentTimeMillis(), delayQueue);
        this.workerThreadPool = Executors.newFixedThreadPool(100);
        this.bossThreadPool = Executors.newFixedThreadPool(1);
        /**20ms推动依次时间轮转动*/
        this.bossThreadPool.submit(() -> {
            for (; ;){
                this.advanceClock(1);
            }
        });
    }

    public void addTimerTaskEntry(TimerTaskEntry entry){
        if (!info.add(entry)) {
            /**已经过期了*/
            TimerTask timerTask = entry.getTimerTask();
            log.info("=====任务：{} 已到期，准备执行=================", timerTask.getDesc());
            workerThreadPool.submit(timerTask);
        }
    }

    @Override
    public void add(TimerTask timerTask) {
        log.info("===============添加任务开始==========task:{}", timerTask.getDesc());
        TimerTaskEntry entry = new TimerTaskEntry(timerTask, timerTask.getDelayMs() + System.currentTimeMillis());
        timerTask.setTimerTaskEntry(entry);
        addTimerTaskEntry(entry);
    }

    /**
     * 推动指针运转获取过期任务
     * @param timeout 时间间隔
     * @return
     */
    @Override
    public void advanceClock(long timeout) {
        try {
            TimerTaskList bucket = delayQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if (Objects.nonNull(bucket)) {
                /**推进时间*/
                info.advanceLock(bucket.getExpiration());
                /**执行过期任务（包括降级）*/
                bucket.clear(this::addTimerTaskEntry);
            }
        } catch (InterruptedException e) {
            log.error("advanceClock error:{}", e);
        }
    }

    @Override
    public int size() {
        /**todo*/
        return 0;
    }

    @Override
    public void shutdown() {
        this.bossThreadPool.shutdown();
        this.workerThreadPool.shutdown();
        this.info = null;
    }
}
