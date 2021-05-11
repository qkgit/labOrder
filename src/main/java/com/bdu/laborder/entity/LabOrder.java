package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;

/**
 * @Author Qi
 * @data 2020/12/26 14:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabOrder {

    /* 预约表id */
    private Integer loId;
    /* 开放类型 1-开放实验室 2-教学实验室 */
    private String loType;
    /* 预约实验室id */
    private Integer lId;
    /* 实验 id */
    private Integer expId;
    //实验室名称
    private String lName;
    //地点
    private String lAddress;
    /* 预约实验 */
    private String expName;
    /* 容量 */
    private int loCap;
    /* 实验室预约剩余人数 */
    private Integer loOdd;
    /* 预约开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /* 预约结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /* 是否满员  */
    private Boolean isFull;
    /* 是否预约状态 1-未开始 2-进行中 3-已结束 */
    private String loState;
    /* 预约时间段 */
    private ArrayList<String> time;
    /* 是否已经被预约 */
    private Boolean isOrder;


}
