package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.mapper.LoginMapper;
import com.bdu.laborder.service.LoginService;
import com.bdu.laborder.utils.JwtUtils;
import com.bdu.laborder.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Qi
 * @data 2020/12/13 18:37
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public SysUser login(String loginName, String password) {
        SysUser user = loginMapper.getUserByUserName(loginName);
        if (user != null) {
            String userPassword = user.getPassword();
            password = MD5Util.MD5Encode(password, "UTF-8");
            if (password.equals(userPassword)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public SysUser getLoginUserById(String id) {
        SysUser user = loginMapper.getUserById(id);
        if (user != null) {
            user.setPassword(null);
            return user;
        }
        return null;
    }


}
