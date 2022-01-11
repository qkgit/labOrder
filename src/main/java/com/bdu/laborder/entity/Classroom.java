package com.bdu.laborder.entity;

import com.bdu.laborder.common.core.domain.BaseEntity;

/**
 * @Title 教室实体表
 * @Author Qi
 * @data 2021/11/16 15:56
 */
public class Classroom extends BaseEntity {

    /** uuid */
    private String uuid;
    /** 名称 */
    private String name;
    /** 位置 */
    private String address;
    /** 容量 */
    private String cap;
    /** 状态 */
    private String status;
    /** 负责人*/
    private String leader;
    /** 删除标识 */
    private String delFlag;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
}
