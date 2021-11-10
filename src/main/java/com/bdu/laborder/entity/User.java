package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.security.RolesAllowed;
import java.io.Serializable;

/**
 * @Author Qi
 * @data 2020/12/9 17:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    /* 用户ID */
    private Integer userId;
    /* 登录名 学号或工号 */
    private String loginName;
    /* 密码 */
    private String password;
    /* 用户真实姓名 */
    private String realName;
    /* 用户身份 0：管理员 1：教师 2：学生 */
    private String roles;
    /* 性别 */
    private String sex;
    /* 年龄 */
    private String age;
    /*所属院系*/
    private String institute;
    /* 专业 */
    private String major;
    /* 班级 */
    private String grade;
    /* 用户头像    https://gitee.com/qkget233/images/raw/master/PiqFjhwzPilZ.jpg */
    private String avatar;
    /* 是否为第一次登录*/
    private String isFirstlogin;

}
