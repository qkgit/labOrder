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
public interface UserService  {

    List<SysUser> getUserList(SysUser user);
    SysUser getUserById(String id);
    int addUser(SysUser user);
    int updateUser(SysUser user);
    int deleteUser(String id);
    int updatePwd(HttpServletRequest request );
    int restPwd(String id);
    int updateUserStatus(String userId,String status);
}
