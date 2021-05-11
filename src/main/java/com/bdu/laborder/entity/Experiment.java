package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** 实验室
 * @Author Qi
 * @data 2020/12/26 23:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experiment implements Serializable {

    /* 实验ID */
    private Integer expId;
    /* 实验名称 */
    private String expName;
    /* 实验类型 */
    private String expType;


}
