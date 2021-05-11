package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Qi
 * @data 2021/4/3 11:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpRequest implements Serializable {
    /* 实验名称 */
    private String eName;
    /* 实验类型 */
    private String eType;

}
