package com.bdu.laborder.entity;

import java.util.Date;

/**
 * @Title
 * @Author Qi
 * @data 2022/4/20 14:01
 */
public class ClassroomOrder {

    /**
     * 主键
     */
    private String uuid;
    /**
     * 教室id
     */
    private String roomId;
    /**
     * 教室位置
     */
    private String roomAddress;
    /**
     * 预约时间
     */
    private Date orderDate;
    /**
     * 预约节数
     */
    private String orderNode;
    /**
     * 预约人数
     */
    private Integer orderNum;
    /**
     * 课程表id(如果查询时间条件下已有课程)
     */
    private String tableId;
    /**
     * 课程表内课程实例
     */
    private CourseTable table;
    /**
     * 教室容量
     */
    private String roomCap;
    /**
     * 教室状态
     * 0: 教室开启
     *   2：教室有课 (1:不可预约)
     *   3：教室无课
     *      4: 成功
     *      5: 失败
     *      6: 审核中。
     *          7：一审
     *          8：二审
     * 1：教室已关闭 (不可预约)
     */
    private String orderStatus;

    public ClassroomOrder() {
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomAddress() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public CourseTable getTable() {
        return table;
    }

    public void setTable(CourseTable table) {
        this.table = table;
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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getRoomCap() {
        return roomCap;
    }

    public void setRoomCap(String roomCap) {
        this.roomCap = roomCap;
    }

    public String getRoomStatus() {
        return orderStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.orderStatus = roomStatus;
    }
}
