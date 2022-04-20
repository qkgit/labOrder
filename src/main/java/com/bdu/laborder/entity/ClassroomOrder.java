package com.bdu.laborder.entity;

/**
 * @Title
 * @Author Qi
 * @data 2022/4/20 14:01
 */
public class ClassroomOrder {

    /** 主键 */
    private String uuid;
    /** 课程表id */
    private String tableId;
    /** 课程表内课程实例 */
    private CourseTable table;
    /** 预约时间 */
    private String orderDate;
    /** 预约人数 */
    private String orderNum;
    /** 教室容量 */
    private String roomCap;
    /** 教室状态
     *  0：教室已关闭（不可预约）
     *  1：教室有课
     *  2：教室无课
     */
    private String roomStatus;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getRoomCap() {
        return roomCap;
    }

    public void setRoomCap(String roomCap) {
        this.roomCap = roomCap;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }
}
