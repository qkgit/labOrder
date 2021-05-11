package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author Qi
 * @data 2021/4/5 18:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabOrderDetil {
    private int lodId;
    private int loId;
    private int userId;
    /* 登录名 学号或工号 */
    private String loginName;
    /* 用户真实姓名 */
    private String realName;
    /* 用户身份 0：管理员 1：教师 2：学生 */
    private String roles;
    /*所属院系*/
    private String institute;
    /* 专业 */
    private String major;
    /* 预约开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /* 预约时间 */
    private Date orderTime;
}
