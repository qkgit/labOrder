package com.bdu.laborder.entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

/**
 * @Title
 * @Author Qi
 * @data 2022/1/5 17:00
 */
public class ClassTime implements Serializable {
    /** 开始时间*/
    private LocalTime startTime;
    /** 结束时间*/
    private LocalTime endTime;

    public ClassTime() {
        this.startTime = LocalTime.now();
        this.endTime = LocalTime.now();
    }

    public ClassTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ClassTime{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
