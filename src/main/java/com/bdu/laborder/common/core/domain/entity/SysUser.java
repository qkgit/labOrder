package com.bdu.laborder.common.core.domain.entity;

import com.bdu.laborder.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @Author Qi
 * @data 2020/12/9 17:59
 */
@Data
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 用户ID */
    private String userId;
    /** 登录名 学号或工号 */
    private String loginName;
    /** 密码 */
    private String password;
    /** 用户真实姓名 */
    private String realName;

    /** 角色对象 */
    private List<SysRole> roles;

    /** 角色组 */
    private String[] roleIds;

    /** 岗位组 */
    private String[] postIds;

    /** 角色ID */
    private String roleId;

    /** 性别 */
    private String sex;
    /** 年龄 */
    private String age;
    /** 所属部门id*/
    private String deptId;
    /** 部门对象*/
    private SysDept dept;
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
    /** 帐号状态（0正常 1停用） */
    private String status;
    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
