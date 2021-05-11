package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Qi
 * @data 2021/4/3 23:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabOrderRequest implements Serializable {
    /* 开放类型 1-开放实验室 2-教学实验室 */
    private String loType;
    //地点
    private String lAddress;
    //实验室名称
    private String lName;
    /* 预约实验 */
    private String expName;


}
