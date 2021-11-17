package com.bdu.laborder.entity;

import com.bdu.laborder.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @Author Qi
 * @data 2020/12/9 17:59
 */
@Data
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 用户ID */
    private Integer userId;
    /** 登录名 学号或工号 */
    private String loginName;
    /** 密码 */
    private String password;
    /** 用户真实姓名 */
    private String realName;
    /** 用户身份 0：管理员 1：教师 2：学生 */
    private String roles;
    /** 性别 */
    private String sex;
    /** 年龄 */
    private String age;
    /** 所属部门id*/
    private String deptId;
    /** 所属院系*/
    private String institute;
    /** 专业 */
    private String major;
    /** 手机号*/
    private String mobile;
    /** 邮箱*/
    private String email;
    /** 用户头像    https://gitee.com/qkget233/images/raw/master/PiqFjhwzPilZ.jpg */
    private String avatar;
    /** 是否为第一次登录*/
    private String isFirstLogin;

}
