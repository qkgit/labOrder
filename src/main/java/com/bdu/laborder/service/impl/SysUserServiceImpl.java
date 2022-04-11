package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.entity.SysDict;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.common.core.domain.entity.SysUserRole;
import com.bdu.laborder.exception.BaseException;
import com.bdu.laborder.mapper.SDictMapper;
import com.bdu.laborder.mapper.SysUserMapper;
import com.bdu.laborder.mapper.SysUserRoleMapper;
import com.bdu.laborder.service.SysUserService;
import com.bdu.laborder.utils.*;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Qi
 * @data 2021/2/4 13:58
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper userMapper;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    SDictMapper dictMapper;
    @Autowired
    SysUserRoleMapper userRoleMapper;

    /**
     *  根据条件分页查询用户列表
     * @param user
     * @return
     */
    @Override
    public List<SysUser> getUserList(SysUser user) {
        List<SysUser> userList = userMapper.getUserList(user);
        return userList;
    }

    /**
     * 通过用户ID查询用户
     * @param id
     * @return
     */
    @Override
    public SysUser getUserById(String id) {
        SysUser user = userMapper.getUserById(id);
        List<String> roleIds = userRoleMapper.selectRoleListByUserId(id);
        user.setRoleIds(roleIds.toArray(new String[roleIds.size()]));
        return user;
    }

    /**
     *  新增用户
     * @param user
     * @return
     */
    @Override
    public int addUser(SysUser user)  {
        user.setPassword(MD5Util.MD5Encode(user.getPassword(),"UTF-8"));
        user.setUserId(UuidUtil.getUuid());
        // 新增用户
        int i = userMapper.addUser(user);
        // 新增用户角色关联
        insertUserRole(user);
        // 新增用户岗位关联
        //insertUserPost(user);

        return i;
    }

    private void insertUserRole(SysUser user) {
        String[] roleIds = user.getRoleIds();
        List<SysUserRole> list = new ArrayList<SysUserRole>();

        if (StringUtils.isNotNull(roleIds)){
            // 新增用户角色信息
            for (String roleId : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
        }else {
            // 添加默认角色 在字典中配置
            SysUserRole ur = new SysUserRole();
            SysDict defaultRole = dictMapper.selectSDictByCode("sys_default_role", "0");
            ur.setUserId(user.getUserId());
            ur.setRoleId(defaultRole.getName());
            list.add(ur);
        }
        if (list.size() > 0){
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     *  更新用户信息
     * @param user
     * @return
     */
    @Override
    public int updateUser(SysUser user) {
        String userId = user.getUserId();

        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
//        // 删除用户与岗位关联
//        userPostMapper.deleteUserPostByUserId(userId);
//        // 新增用户与岗位管理
//        insertUserPost(user);

        return userMapper.updateUser(user);

    }

    @Override
    public int deleteUser(String[] ids) {
        for (String id : ids) {
            checkUserAllowed(new SysUser(id));
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(ids);
        // 删除用户与岗位关联
//        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(ids);
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
            throw new BaseException(BussinessCode.OLD_PWD_FAIL);
        }
        // 获取新密码  --- 去除空格
        String pwd = jsonParam.get("pwd").toString().replace(" ", "");
        // 判断长度大于6
        if (pwd.length() < 6) {
            throw new BaseException(BussinessCode.UPDATE_PWD_NULL);
        }
        // 对密码进行加密
        pwd = MD5Util.MD5Encode(pwd, "UTF-8");
        return  userMapper.updatePwd(userId, pwd);


    }

    @Override
    public int restPwd(String[] ids) {
        int i = 0;
        for (String id : ids) {
            //通过id 查询用户
            SysUser user = userMapper.getUserById(id);
            checkUserAllowed(user);
            try {
                String password = MD5Util.MD5Encode(user.getLoginName().substring(user.getLoginName().length()-6),"UTF-8");
                i += userMapper.restPwd(user.getUserId(),password);
            } catch (Exception e) {
                i = 0;
                e.printStackTrace();
            }
        }
        return i;
    }

    @Override
    public int updateUserStatus(String userId, String status) {
        SysUser user = getUserById(userId);
        user.setStatus(status);
        return userMapper.updateUserStatus(user);
    }

    /**
     *  校验登录名是否唯一
     * @param loginName 用户登录名（学号或工号）
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName) {
        int count = userMapper.checkUserNameUnique(loginName);
        return count>0? UserConstants.NOT_UNIQUE : UserConstants.UNIQUE;
    }

    /**
     *  校验手机号是否唯一
     * @param user 用户信息
     * @return 1：是   0：否
     */
    @Override
    public String checkMobileUnique(SysUser user) {
        SysUser info = userMapper.checkPhoneUnique(user.getMobile());
        if (StringUtils.isNotNull(info) && !info.getUserId().equals(user.getUserId())){
            return UserConstants.NOT_UNIQUE;
        }else {
            return UserConstants.UNIQUE;
        }
    }

    /**
     * 校验Email是否唯一
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && !info.getUserId().equals(user.getUserId())){
            return UserConstants.NOT_UNIQUE;
        }else {
            return UserConstants.UNIQUE;
        }
    }

    /**
     * 校验用户是否允许操作
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new BaseException("不允许操作超级管理员用户");
        }
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
