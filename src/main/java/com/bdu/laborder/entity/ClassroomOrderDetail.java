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
    /** 预约表id */
    private String orderId;
    /** 预约人id */
    private String orderUser;
    /** 预约人姓名 */
    private String userName;
    /** 预约教室id */
    private String classroomId;
    /** 预约教室 */
    private String classroomName;
    /** 预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    /** 预约节数 */
    private String orderNode;
    /** 预约状态
     *    6：一审
     *    7：二审
     *    8: 成功
     *    9: 失败
     */
    private String orderStatus;
    /** 创建时间 */
    private Date createTime;
    /** 预约说明 */
    private String remark;
    /** 审核情况 */
    private List<OrderAudit> orderAudit;
    /** 删除标识 */
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
