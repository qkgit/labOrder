package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Qi
 * @data 2020/12/9 17:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Labs implements Serializable {

    //ID
    private Integer lId;
    //类型 1-物理 2-化学 3-生物
    private String lType;
    //实验室名称
    private String lName;
    //地点
    private String lAddress;
    //容量
    private String lCap;


}
