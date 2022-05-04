package com.bdu.laborder.entity;

import java.util.Date;

/**
 * @Title  预约审核表
 * @Author Qi
 * @data 2022/5/3 16:59
 */
public class OrderAudit {
    private String uuid;
    /** 预约记录id */
    private String orderRecordId;
    /** 添加意见人id */
    private String reviewUserId;
    /** 添加意见人 */
    private String reviewUser;
    /** 类型 1:一审 2：二审 */
    private String type;
    /** 意见内容 */
    private String reviewRemark;
    /** 意见添加时间 */
    private Date reviewTime;
    /** 审核状态 0：未办 1：已办 */
    private String state;
    /** 添加时间 */
    private Date createTime;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderRecordId() {
        return orderRecordId;
    }

    public void setOrderRecordId(String orderRecordId) {
        this.orderRecordId = orderRecordId;
    }

    public String getReviewUserId() {
        return reviewUserId;
    }

    public void setReviewUserId(String reviewUserId) {
        this.reviewUserId = reviewUserId;
    }

    public String getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(String reviewUser) {
        this.reviewUser = reviewUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReviewRemark() {
        return reviewRemark;
    }

    public void setReviewRemark(String reviewRemark) {
        this.reviewRemark = reviewRemark;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
