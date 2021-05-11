package com.bdu.laborder.service;

import com.bdu.laborder.entity.User;

/**
 * @Author Qi
 * @data 2020/12/13 18:23
 */

public interface LoginService {

    /**
     *  登录功能 验证用户登录信息是否正确
     * */
    User login(String loginName,String password);

    /**
     * 根据id查询用户
     * @param token
     * @return
     */
    User selectUserByToken(String token);

}
