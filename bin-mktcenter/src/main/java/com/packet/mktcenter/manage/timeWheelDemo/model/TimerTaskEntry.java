package com.packet.mktcenter.manage.timeWheelDemo.model;

import lombok.Data;

import java.util.Objects;
import com.packet.mktcenter.manage.timeWheelDemo.service.impl.TimerTask;

/**
 * 存储任务的容器entry
 */
@Data
public class TimerTaskEntry implements Comparable<TimerTaskEntry> {

    private TimerTask timerTask;
    private long expireMs;
    volatile TimerTaskList timerTaskList;
    TimerTaskEntry next;
    TimerTaskEntry prev;

    /**
     * 构造方法
     * @param timerTask
     * @param expireMs
     */
    public TimerTaskEntry(TimerTask timerTask, long expireMs) {
        this.timerTask = timerTask;
        this.expireMs = expireMs;
        this.next = null;
        this.prev = null;
    }

    /**
     * 任务移除方法
     */
    public void remove(){
        TimerTaskList currentList = timerTaskList;
        while (Objects.nonNull(currentList)) {
            currentList.remove(this);
            currentList = timerTaskList;
        }
    }

    @Override
    public int compareTo(TimerTaskEntry o) {
        return (int) (this.expireMs - o.expireMs);
    }
}
