package com.bdu.laborder.service;

import com.bdu.laborder.common.core.domain.entity.SysUser;

/**
 * @Author Qi
 * @data 2020/12/13 18:23
 */

public interface LoginService {

    /**
     *  登录功能 验证用户登录信息是否正确
     * */
    SysUser login(String loginName, String password);

    /**
     * 获取登录用户信息
     * @return
     */
    SysUser getLoginUserById(String id);

}
