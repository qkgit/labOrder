package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Qi
 * @data 2021/4/2 13:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements Serializable {

    /* 登录名 学号或工号 */
    private String loginName;
    /* 用户身份 0：管理员 1：教师 2：学生 */
    private String roles;
    /*所属院系*/
    private String institute;
    /* 专业 */
    private String major;

}
