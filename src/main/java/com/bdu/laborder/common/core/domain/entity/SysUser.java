package com.bdu.laborder.common.core.domain.entity;

import com.bdu.laborder.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Qi
 * @data 2020/12/9 17:59
 */
@Data
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 所属部门id
     */
    private String deptId;

    /**
     * 登录名 学号或工号
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 性别
     */
    private String sex;

    /**
     * 用户头像    https://gitee.com/qkget233/images/raw/master/PiqFjhwzPilZ.jpg
     */
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 是否为第一次登录
     */
    private String isFirstLogin;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String delFlag;

    /**
     * 部门对象
     */
    private SysDept dept;

    /**
     * 角色组
     */
    private String[] roleIds;

    /**
     * 角色ID(用户管理搜索用户项)
     */
    private String roleId;


    /**
     * 角色对象
     */
    private List<SysRole> roles;

    /**
     * 岗位组  留有升级
     */
    private String[] postIds;




    public SysUser() {
    }

    public SysUser(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "SysUser{" +
                "userId='" + userId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex='" + sex + '\'' +
                ", avatar='" + avatar + '\'' +
                ", status='" + status + '\'' +
                ", isFirstLogin='" + isFirstLogin + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", dept=" + dept +
                ", roleIds=" + Arrays.toString(roleIds) +
                ", roleId='" + roleId + '\'' +
                ", roles=" + roles +
                ", postIds=" + Arrays.toString(postIds) +
                "} " + super.toString();
    }
}
