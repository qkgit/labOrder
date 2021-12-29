package com.bdu.laborder.service;

import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Qi
 * @data 2021/2/4 13:58
 */
public interface SysUserService {

    /**
     *  根据条件分页查询用户列表
     * @param user
     * @return
     */
    List<SysUser> getUserList(SysUser user);

    /**
     *  通过用户ID查询用户
     * @param id
     * @return
     */
    SysUser getUserById(String id);

    /**
     *  新增用户
     * @param user
     * @return
     */
    int addUser(SysUser user);

    /**
     *  更新用户信息
     * @param user
     * @return
     */
    int updateUser(SysUser user);

    int deleteUser(String[] ids);
    int updatePwd(HttpServletRequest request );
    int restPwd(String[] ids);
    int updateUserStatus(String userId,String status);

    /**
     * 校验登录名是否唯一
     *
     * @param loginName 用户登录名（学号或工号）
     * @return 结果
     */
    public String checkLoginNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkMobileUnique(SysUser user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkEmailUnique(SysUser user);

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    public void checkUserAllowed(SysUser user);
}
