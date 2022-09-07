package com.packet.mktcenter.manage.timeDemo.model;

public abstract class ITask implements Runnable {

    public Integer offset;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}
