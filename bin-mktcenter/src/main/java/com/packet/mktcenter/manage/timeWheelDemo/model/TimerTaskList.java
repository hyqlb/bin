package com.packet.mktcenter.manage.timeWheelDemo.model;

import lombok.Data;

import java.util.Objects;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

@Data
public class TimerTaskList implements Delayed {

    private TimerTaskEntry root = new TimerTaskEntry(null, -1);

    {
        root.next = root;
        root.prev = root;
    }

    /**bucket的过期时间*/
    private AtomicLong expiration = new AtomicLong(-1L);

    public long getExpiration(){
        return expiration.get();
    }

    /**
     * 设置bucket的过期时间，设置成功返回true
     * @param expirationMs
     * @return
     */
    boolean setExpiration(long expirationMs){
        return expiration.getAndSet(expirationMs) != expirationMs;
    }

    /**
     * 往链表中增加一个任务
     * @param entry
     * @return
     */
    public boolean addTask(TimerTaskEntry entry){
        boolean done = false;
        while (!done) {
            /**如果TimerTaskEntry已经在别的list中就先移除，同步代码块外面移除，避免死锁，一直到成功为止*/
            entry.remove();
            synchronized (this) {
                if (Objects.isNull(entry.timerTaskList)) {
                    /**加到链表的末尾*/
                    entry.timerTaskList = this;
                    TimerTaskEntry tail = root.prev;
                    entry.prev = tail;
                    entry.next = root;
                    tail.next = entry;
                    root.prev = entry;
                    done = true;
                }
            }
        }
        return true;
    }

    /**
     * 从TimedTaskList移除指定的TimerTaskEntry
     * @param entry
     */
    public void remove(TimerTaskEntry entry){
        synchronized (this) {
            if (entry.getTimerTaskList().equals(this)) {
                entry.next.prev = entry.prev;
                entry.prev.next = entry.next;
                entry.next = null;
                entry.prev = null;
                entry.timerTaskList = null;
            }
        }
    }

    /**
     * 移除所有
     * @param entry
     */
    public synchronized void clear(Consumer<TimerTaskEntry> entry){
        TimerTaskEntry head = root.next;
        while (!head.equals(root)) {
            remove(head);
            entry.accept(head);
            head = root.next;
        }
        expiration.set(-1L);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return Math.max(0, unit.convert(expiration.get() - System.currentTimeMillis(), TimeUnit.MILLISECONDS));
    }

    @Override
    public int compareTo(Delayed o) {
        if (o instanceof TimerTaskList) {
            return Long.compare(expiration.get(), ((TimerTaskList) o).expiration.get());
        }
        return 0;
    }
}
