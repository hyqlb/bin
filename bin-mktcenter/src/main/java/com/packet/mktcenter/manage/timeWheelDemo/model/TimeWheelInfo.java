package com.packet.mktcenter.manage.timeWheelDemo.model;

import java.util.Objects;
import java.util.concurrent.DelayQueue;

public class TimeWheelInfo {
    /**一个槽的时间间隔（时间轮最小刻度）*/
    private long tickMs;
    /**时间轮大小（槽的个数）*/
    private int wheelSize;
    /**一轮的时间跨度*/
    private long interval;
    /**当前时间*/
    private long currentTime;
    /**槽*/
    private TimerTaskList[] buckets;
    /**上层的时间轮*/
    private volatile TimeWheelInfo overflowWheel;
    /**一个timer只有一个delayqueue*/
    private DelayQueue<TimerTaskList> delayQueue;

    public TimeWheelInfo(long tickMs, int wheelSize, long currentTime, DelayQueue<TimerTaskList> delayQueue) {
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.currentTime = currentTime;
        this.delayQueue = delayQueue;
        this.interval = tickMs * wheelSize;
        this.buckets = new TimerTaskList[wheelSize];
        this.currentTime = currentTime - (currentTime % tickMs);
        for (int i = 0; i < wheelSize; i++) {
            buckets[i] = new TimerTaskList();
        }
    }

    public boolean add(TimerTaskEntry entry){
        long expiration = entry.getExpireMs();
        if (expiration < tickMs + currentTime) {
            /**到期了*/
            return false;
        } else if (expiration < currentTime + interval) {
            /**扔进当前时间轮的某个槽中，只有时间大于某个槽，才会放进去*/
            long virtualId = (expiration / tickMs);
            int index = (int) (virtualId % wheelSize);
            TimerTaskList bucket = buckets[index];
            bucket.addTask(entry);
            /**设置bucket过期时间*/
            if (bucket.setExpiration(virtualId * tickMs)) {
                /**设置好过期时间的bucket需要入队*/
                delayQueue.offer(bucket);
                return true;
            }
        } else {
            /**当前轮不能满足，需要扔到上一轮*/
            TimeWheelInfo info = getOverflowWheel();
            return info.add(entry);
        }
        return false;
    }

    private TimeWheelInfo getOverflowWheel(){
        if (Objects.isNull(overflowWheel)) {
            synchronized (this) {
                if (Objects.isNull(overflowWheel)) {
                    overflowWheel = new TimeWheelInfo(interval, wheelSize, currentTime, delayQueue);
                }
            }
        }
        return overflowWheel;
    }

    /**
     * 推进指针
     * @param timestamp
     */
    public void advanceLock(long timestamp){
        if (timestamp > currentTime + tickMs) {
            currentTime = timestamp - (timestamp % tickMs);
            if (Objects.nonNull(overflowWheel)) {
                this.getOverflowWheel().advanceLock(timestamp);
            }
        }
    }
}
