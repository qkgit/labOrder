package com.bdu.laborder.entity;

import com.bdu.laborder.common.core.domain.BaseEntity;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/1/5 16:43
 */
public class CourseTime extends BaseEntity {

    private String uuid;
    /** 课程节数*/
    private int num;
    /** 时间段*/
    private String classTime;
    private List<ClassTime> times;
    /** 是否默认*/
    private String isDefault;
    /** 版本号*/
    private int version;

    public CourseTime() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public List<ClassTime> getTimes() {
        return times;
    }

    public void setTimes(List<ClassTime> times) {
        this.times = times;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "CourseTime{" +
                "uuid='" + uuid + '\'' +
                ", num=" + num +
                ", classTime='" + classTime + '\'' +
                ", times=" + times +
                ", isDefault='" + isDefault + '\'' +
                ", version=" + version +
                "} " + super.toString();
    }
}
