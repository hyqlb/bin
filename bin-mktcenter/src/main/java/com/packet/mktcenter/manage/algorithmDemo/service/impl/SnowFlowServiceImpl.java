package com.packet.mktcenter.manage.algorithmDemo.service.impl;

import com.packet.mktcenter.manage.algorithmDemo.model.SnowFlowInfo;
import com.packet.mktcenter.manage.algorithmDemo.service.SnowFlowService;
import org.springframework.stereotype.Service;

@Service
public class SnowFlowServiceImpl implements SnowFlowService {

    @Override
    public Long createSnowFlow(SnowFlowInfo info) {
        long snowFlowId = this.nextId(info);
        return snowFlowId;
    }

    public synchronized long nextId(SnowFlowInfo info){
        long lastTimestamp = info.getLastTimestamp();
        long sequence = info.getSequence();
        long twepoch = info.getTwepoch();
        long workerId = info.getWorkerId();
        long datacenterId = info.getDatacenterId();
        long timestampLeftShift = info.getTimestampLeftShift();
        long workerIdShift = info.getWorkerIdShift();
        long datacenterIdShift = info.getDatacenterIdShift();
        long sequenceMask = info.getSequenceMask();
        /**获取当前时间戳，单位是毫秒*/
        long timestamp = timeGen();
        /**判断是否小于上次时间戳，如果小于的话，就抛出异常*/
        if (timestamp < lastTimestamp){
            throw new RuntimeException(String.format("clock is moving backwards. Resusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        /**如果在同一毫秒，又发送了一个请求生成ID，这个时候就把sequence序号递增1，最多到4096*/
        if (timestamp == lastTimestamp) {
            /**在一个毫秒内最多只能有4096个数字，避免传递的squence超过了4096这个范围*/
            sequence = (sequence + 1) & sequenceMask;
            /**当某一毫秒的时间，产生的ID数超过4095，系统会进入等待状态，直到下一毫秒，系统继续产生ID*/
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            /**阻塞到下一毫秒，获得新的时间戳*/
            sequence = 0;
        }
        /**记录上次生成ID的时间戳，单位是毫秒*/
        lastTimestamp = timestamp;
        /**
         * 先将时间戳左移，放到41bit；将数据中心ID左移放到5bit；将机器ID左移放到5bit；将序号放在最后12bit
         * 最后拼接成一个64bit的二进制数字，转换成10进制就是个long类型
         * */
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) | sequence;
    }

    /**
     * 当某一毫秒的时间，产生的ID数超过4095，系统会进入等待，直到下一毫秒，系统继续产生ID
     * @param lastTimestamp 上次生成ID的时间戳
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp){
        long timestamp = timeGen();
        while(timestamp <= lastTimestamp){
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取当前时间戳
     * 返回以毫秒为单位的当前时间
     * @return
     */
    private long timeGen(){
        return System.currentTimeMillis();
    }
}
