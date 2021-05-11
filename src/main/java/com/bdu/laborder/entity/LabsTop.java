package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/** 实验室预约排行榜
 * @Author Qi
 * @data 2021/4/5 15:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabsTop implements Serializable {
    private int ltId;
    private int lId;
    private String newestDate;
    private int allTime;
    //实验室名称
    private String lName;
    //地点
    private String lAddress;

}
