package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.BussinessCode;
import com.bdu.laborder.entity.User;
import com.bdu.laborder.entity.UserRequest;
import com.bdu.laborder.exception.LabOrderException;
import com.bdu.laborder.mapper.UserMapper;
import com.bdu.laborder.service.UserService;
import com.bdu.laborder.utils.CreateGson;
import com.bdu.laborder.utils.JwtUtils;
import com.bdu.laborder.utils.MD5Util;
import com.bdu.laborder.utils.PageQuery;
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
    public PageInfo<User> getUserList(PageQuery pageQuery) {
        PageInfo page = pageQuery.getPage();
        Gson gson = CreateGson.createGson();
        UserRequest item = gson.fromJson(JSONObject.fromObject(pageQuery.getItem()).toString(), UserRequest.class);
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<User> userList = userMapper.getUserList(item);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;

    }

    @Override
    public User getUserById(Integer id) {
        User user = userMapper.getUserById(id);
        return user;
    }

    @Override
    public int addUser(User user)  {
        String loginName = user.getLoginName();
        if(userMapper.selectUserByLoginName(loginName) != null){
            throw new LabOrderException(BussinessCode.USER_NAME_REREAT);
        }
        String pwd = user.getPassword();
        pwd = MD5Util.MD5Encode(pwd,"UTF-8");
        user.setPassword(pwd);
        int i = userMapper.addUser(user);
        if (i != 0){
            return i;
        }
        return 0;
    }

    @Override
    public int updateUser(User user) {
        int i = userMapper.updateUser(user);
        if (i!=0){
            return i;
        }
        return 0;
    }

    @Override
    public int deleteUser(Integer id) {
        int i = userMapper.deleteUser(id);
        return i;
    }

    @Override
    public int updatePwd(HttpServletRequest request) {
        // ??????token
        String token = request.getHeader("X-Token");
        // ??????token
        Claims claims = jwtUtils.parseJwt(token);
        // ??????id
        String userId = claims.getId();
        int id = Integer.parseInt(userId);
        String password = getUserById(id).getPassword();

        com.alibaba.fastjson.JSONObject jsonParam = this.getJSONParam(request);
        String oldPwd = jsonParam.get("oldPwd").toString();
        String oldPassWord = MD5Util.MD5Encode(oldPwd, "UTF-8");
        // ???????????????????????????
        if(password.equals(oldPassWord) == false) {
            throw new LabOrderException(BussinessCode.OLD_PWD_FAIL);
        }
        // ???????????????  --- ????????????
        String pwd = jsonParam.get("pwd").toString().replace(" ", "");
        // ??????????????????6
        if (pwd.length() < 6) {
            throw new LabOrderException(BussinessCode.UPDATE_PWD_NULL);
        }
        // ?????????????????????
        pwd = MD5Util.MD5Encode(pwd, "UTF-8");
        int i = userMapper.updatePwd(id, pwd);
        return i;


    }

    @Override
    public int restPwd(Integer id) {
        //??????id ????????????
        User user = userMapper.getUserById(id);
        String loginName = user.getLoginName();
        String password = loginName.substring(loginName.length()-6);
        password = MD5Util.MD5Encode(password,"UTF-8");

        int i = userMapper.restPwd(user.getUserId(),password);
        return i;
    }

    /**
     *  ??????request??????????????????
     * @param request
     * @return
     */
    public com.alibaba.fastjson.JSONObject getJSONParam(HttpServletRequest request){
        com.alibaba.fastjson.JSONObject jsonParam = null;
        try {
            // ???????????????
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            // ???????????????Stringbuilder
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
