package com.packet.mktcenter.manage.snowFlowDemo.model;

import lombok.Data;

@Data
public class SnowFlowInfo {
    /**因为二进制里第一个 bit 为如果是 1，那么都是负数，但是我们生成的 id 都是正数，所以第一个 bit 统一都是 0。*、

    /**开始时间戳（2015-01-01）*/
    private final long twepoch = 1420041600000L;
    /**机器ID所占的位数*/
    private final long workerIdBits = 5L;
    /**数据标识ID所占的位数*/
    private final long datacenterIdBits = 5L;
    /**支持的最大机器ID，结果是31（这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数）*/
    private final long maxWorkerId = -1L^(-1L << workerIdBits);
    /**支持的最大数据标识ID，结果是31*/
    private final long maxDatacenterId = -1L^(-1L << datacenterIdBits);
    /**序列在ID中所占的位数*/
    private final long sequenceBits = 12L;
    /**机器ID向左移12位*/
    private final long workerIdShift = sequenceBits;
    /**数据标识ID向左移17位（12+5）*/
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    /**时间戳向左移22位（5+5+12）*/
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    /**生成序列的掩码，这里是4095（0b111111111111=0xffff=4095）
    -1L 二进制就是1111 1111
    -1 左移12位就是 1111  1111 0000 0000 0000 0000
    异或  相同为0 ，不同为1
    1111  1111  0000  0000  0000  0000
    ^
    1111  1111  1111  1111  1111  1111
    0000 0000 1111 1111 1111 1111 换算成10进制就是4095*/
    private final long sequenceMask = -1L^(-1L << sequenceBits);
    /**工作机器ID（0~31）*/
    private long workerId;
    /**数据中心ID（0~31）*/
    private long datacenterId;
    /**代表一毫秒内生成的多个id的最新序号  12位 4096 -1 = 4095 个*/
    private long sequence = 0L;
    /**上次生成ID的时间戳，记录产生时间毫秒数，判断是否是同1毫秒*/
    private long lastTimestamp = -1L;

    public long getTimestamp(){
        return System.currentTimeMillis();
    }

    /**
     * 无参构造方法
     */
    public SnowFlowInfo(){

    }

    /**
     * 构造方法
     * @param workerId
     * @param datacenterId
     * @param sequence
     */
    public SnowFlowInfo(long workerId, long datacenterId, long sequence) {
        /**检查数据中心D和机器ID是否超过31，是否小于0*/
        if (workerId > maxWorkerId || workerId < 0){
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId)
            );
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0){
            throw new IllegalArgumentException(
                    String.format("datacenterId Id can't be greater than %d or less than 0", maxDatacenterId)
            );
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }
}
