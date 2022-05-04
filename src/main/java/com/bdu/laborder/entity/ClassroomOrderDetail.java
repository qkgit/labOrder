package com.bdu.laborder.entity;

import com.bdu.laborder.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/4/22 16:04
 */
public class ClassroomOrderDetail {
    private String uuid;
    private String orderUser;
    private String orderId;
    private String classroomId;
    private String classroomName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date orderDate;
    private String orderNode;
    private String orderStatus;
    private Date createTime;
    private String remark;
    private List<OrderAudit> orderAudit;
    private String deleteFlag;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNode() {
        return orderNode;
    }

    public void setOrderNode(String orderNode) {
        this.orderNode = orderNode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<OrderAudit> getOrderAudit() {
        return orderAudit;
    }

    public void setOrderAudit(List<OrderAudit> orderAudit) {
        this.orderAudit = orderAudit;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public void toLeaderCheck(){
        this.setOrderStatus(Constant.ORDER_STATUS_LEADER_CHECK);
    }

    public void toSecCheck() {
        this.setOrderStatus(Constant.ORDER_STATUS_SEC_CHECK);
    }

    public void toComplete() {
        this.setOrderStatus(Constant.ORDER_STATUS_COMPLETE);
    }

    public void toNotPass() {
        this.setOrderStatus(Constant.ORDER_STATUS_NOT_PASS);
    }

    public void setTimeOut() {
        this.setOrderStatus(Constant.ORDER_STATUS_TIME_OUT);
    }



}
