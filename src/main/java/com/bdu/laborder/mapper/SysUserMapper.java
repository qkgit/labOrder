package com.bdu.laborder.mapper;

import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.entity.UserRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/2/4 13:57
 */
@Repository
@Mapper
public interface SysUserMapper {

    /**
     * 查询用户列表
     */
    List<SysUser> getUserList(SysUser item);

    /**
     *  根据id查询用户信息
     */
    SysUser getUserById(@Param("id") String id);

    /**
     *  新增用户
     */
    int addUser(SysUser user);

    /**
     * 修改用户
     */
    int updateUser(SysUser user);

    /**
     *  删除用户
     */
    int deleteUser(String id);

    /**
     * 根据登录名查找用户
     */
    Integer selectUserByLoginName( String loginName);

    /**
     *  修改密码
     */
    int updatePwd(@Param("id") String id,@Param("pwd") String pwd);

    /**
     * 修改用户头像
     * @param id
     * @param url
     * @return
     */
    int updateUserAvatar(@Param("id") String id,@Param("url") String url);

    int restPwd(@Param("id")String id,@Param("pwd")String pwd);

    int  updateUserStatus(SysUser user);

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 用户名称
     * @return 结果
     */
    public int checkUserNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     *
     * @param mobile 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String mobile);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public SysUser checkEmailUnique(String email);
}
