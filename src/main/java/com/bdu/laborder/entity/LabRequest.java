package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Qi
 * @data 2021/4/2 13:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabRequest implements Serializable {
    //类型 1-物理 2-化学 3-生物
    private String lType;
    //实验室名称
    private String lName;
    //地点
    private String lAddress;

}
