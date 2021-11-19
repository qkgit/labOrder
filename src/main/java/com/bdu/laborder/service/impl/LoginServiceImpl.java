package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.mapper.LoginMapper;
import com.bdu.laborder.service.LoginService;
import com.bdu.laborder.utils.JwtUtils;
import com.bdu.laborder.utils.MD5Util;
import io.jsonwebtoken.Claims;
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
        if (user != null){
            String userPassword = user.getPassword();
            password = MD5Util.MD5Encode(password,"UTF-8");
            if (password.equals(userPassword)){
                return user;
            }
        }
        return null;
    }

    @Override
    public SysUser selectUserByToken(String token) {
        //根据token获取用户数据
        Claims claims = null;
        try {
            //解析token
            claims = jwtUtils.parseJwt(token);
            //获取clamis
            String userId = claims.getId();
            //根据id查询用户信息
            SysUser user = loginMapper.getUserById(userId);
            if (user !=null){
                user.setPassword(null);
                return user;
            }
            return  null;
        }catch (Exception e){
            //如果token过期或失败 返回错误并重新登录
            e.printStackTrace();
            return null;
        }
    }


}
