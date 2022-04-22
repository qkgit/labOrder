package com.bdu.laborder.entity;

import java.util.Date;

/**
 * @Title  教室预约查询请求
 * @Author Qi
 * @data 2022/4/21 10:06
 */
public class ClassroomOrderRequest {

    /** 预约时间 */
    private Date orderDate;
    /** 预约周 */
    private String orderWeek;
    /** 预约节数 */
    private String orderNode;
    /** 教室楼层 */
    private String level;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderWeek() {
        return orderWeek;
    }

    public void setOrderWeek(String orderWeek) {
        this.orderWeek = orderWeek;
    }

    public String getOrderNode() {
        return orderNode;
    }

    public void setOrderNode(String orderNode) {
        this.orderNode = orderNode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
