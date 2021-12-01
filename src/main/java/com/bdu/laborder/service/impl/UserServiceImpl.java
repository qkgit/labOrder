package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.entity.UserRequest;
import com.bdu.laborder.exception.LabOrderException;
import com.bdu.laborder.mapper.UserMapper;
import com.bdu.laborder.service.UserService;
import com.bdu.laborder.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @Author Qi
 * @data 2021/2/4 13:58
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public List<SysUser> getUserList(SysUser user) {
        List<SysUser> userList = userMapper.getUserList(user);
        return userList;
    }

    @Override
    public SysUser getUserById(String id) {
        SysUser user = userMapper.getUserById(id);
        return user;
    }

    @Override
    public int addUser(SysUser user)  {
        String loginName = user.getLoginName();
        if(userMapper.selectUserByLoginName(loginName) != null){
            throw new LabOrderException(BussinessCode.USER_NAME_REREAT);
        }
        String pwd = user.getPassword();
        pwd = MD5Util.MD5Encode(pwd,"UTF-8");
        user.setPassword(pwd);
        user.setUserId(UuidUtil.getUuid());
        int i = userMapper.addUser(user);
        if (i != 0){
            return i;
        }
        return 0;
    }

    @Override
    public int updateUser(SysUser user) {
        int i = userMapper.updateUser(user);
        if (i!=0){
            return i;
        }
        return 0;
    }

    @Override
    public int deleteUser(String id) {
        int i = userMapper.deleteUser(id);
        return i;
    }

    @Override
    public int updatePwd(HttpServletRequest request) {
        // 获取token
        String token = request.getHeader("X-Token");
        // 解析token
        Claims claims = jwtUtils.parseJwt(token);
        // 获取id
        String userId = claims.getId();

        String password = getUserById(userId).getPassword();

        com.alibaba.fastjson.JSONObject jsonParam = this.getJSONParam(request);
        String oldPwd = jsonParam.get("oldPwd").toString();
        String oldPassWord = MD5Util.MD5Encode(oldPwd, "UTF-8");
        // 判断老密码是否一致
        if(password.equals(oldPassWord) == false) {
            throw new LabOrderException(BussinessCode.OLD_PWD_FAIL);
        }
        // 获取新密码  --- 去除空格
        String pwd = jsonParam.get("pwd").toString().replace(" ", "");
        // 判断长度大于6
        if (pwd.length() < 6) {
            throw new LabOrderException(BussinessCode.UPDATE_PWD_NULL);
        }
        // 对密码进行加密
        pwd = MD5Util.MD5Encode(pwd, "UTF-8");
        return  userMapper.updatePwd(userId, pwd);


    }

    @Override
    public int restPwd(String id) {
        //通过id 查询用户
        SysUser user = userMapper.getUserById(id);
        String loginName = user.getLoginName();
        String password = loginName.substring(loginName.length()-6);
        password = MD5Util.MD5Encode(password,"UTF-8");

        int i = userMapper.restPwd(user.getUserId(),password);
        return i;
    }

    /**
     *  获取request中携带的参数
     * @param request
     * @return
     */
    public com.alibaba.fastjson.JSONObject getJSONParam(HttpServletRequest request){
        com.alibaba.fastjson.JSONObject jsonParam = null;
        try {
            // 获取输入流
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            // 写入数据到Stringbuilder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonParam = com.alibaba.fastjson.JSONObject.parseObject(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonParam;
    }
}
