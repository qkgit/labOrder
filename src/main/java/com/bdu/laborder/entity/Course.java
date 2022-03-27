package com.bdu.laborder.entity;

import com.bdu.laborder.common.core.domain.BaseEntity;

/**
 * @Title 课程实体表
 * @Author Qi
 * @data 2022/3/24 15:37
 */
public class Course extends BaseEntity {
    private String uuid;
    /** 课程名称 */
    private String name;
    /** 教师 */
    private String leader;
    /** 教师id */
    private String leaderId;
    /** 类型 1-考查课 2-考试课 */
    private String type;
    /** 课程学分 */
    private String credit;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}
