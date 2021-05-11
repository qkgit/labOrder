package com.bdu.laborder.service;

import com.bdu.laborder.entity.User;
import com.bdu.laborder.utils.PageQuery;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Qi
 * @data 2021/2/4 13:58
 */
public interface UserService  {

    PageInfo<User> getUserList(PageQuery pageQuery);
    User getUserById(Integer id);
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(Integer id);
    int updatePwd(HttpServletRequest request );
    int restPwd(Integer id);
}
