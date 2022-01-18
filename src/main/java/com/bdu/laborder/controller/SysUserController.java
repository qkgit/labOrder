package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.common.core.result.ResultGenerator;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.service.SysUserService;
import com.bdu.laborder.utils.PageQuery;
import com.bdu.laborder.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/** 用户管理控制层
 * @Author Qi
 * @data 2021/2/4 13:56
 */
@RestController
public class SysUserController extends BaseController {

    @Autowired
    SysUserService userService;

    @PostMapping("/users")
    public Result getUserList(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        SysUser user = getParam(pageQuery, SysUser.class);
        List<SysUser> userList = userService.getUserList(user);
        return  getPageInfo(userList);
    }

    @GetMapping("/user/{id}")
    public Result getUserById(@PathVariable String id){
        if (StringUtils.isNotNull(id)){
            SysUser user = userService.getUserById(id);
            if (user == null){
                return error();
            }
            return success(user);
        }else {
            return  ResultGenerator.returnCodeMessage(BussinessCode.RESULT_FIELD_NULL);
        }
    }

    @PostMapping("/user")
    public Result addUser(@RequestBody SysUser user) {
        String loginName = user.getLoginName();
        if (UserConstants.NOT_UNIQUE.equals(userService.checkLoginNameUnique(loginName))) {
            return error("新增用户'"+user.getRealName()+"'失败，学号或工号已存在！");
        }
        if (StringUtils.isNotEmpty(user.getMobile())
                && UserConstants.NOT_UNIQUE.equals(userService.checkMobileUnique(user))){
            return error("新增用户'"+user.getRealName()+"'失败，手机号码已存在！");
        }
        if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))){
            return error("新增用户'"+user.getRealName()+"'失败，邮箱已存在！");
        }
        if (loginName.length()<6){
            return error("新增用户'"+user.getRealName()+"'失败，学号或工号格式不符合规范！");
        }
        String password = loginName.substring(loginName.length()-6);
        user.setPassword(password);
        user.setCreateBy(getUserName());
        return toResult(userService.addUser(user));
    }

    @PutMapping("/user")
    public Result updateUser(@RequestBody SysUser user){
        userService.checkUserAllowed(user);
        if (StringUtils.isNotEmpty(user.getMobile())
                && UserConstants.NOT_UNIQUE.equals(userService.checkMobileUnique(user))){
            return error("新增用户'"+user.getRealName()+"'失败，手机号码已存在！");
        }
        if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))){
            return error("新增用户'"+user.getRealName()+"'失败，邮箱已存在！");
        }
        user.setUpdateBy(getUserName());
        return toResult(userService.updateUser(user));
    }

    @DeleteMapping("/user/{ids}")
    public Result deleteUser(@PathVariable String[] ids){
        if (ArrayUtils.contains(ids, getUserId())) {
            return error("当前用户不能删除");
        }
        return toResult(userService.deleteUser(ids));
    }

    @PutMapping("/user/changeStatus")
    public Result changeUserStatus(@RequestParam(value = "userId") String userId,@RequestParam(value = "status") String status){
        return toResult(userService.updateUserStatus(userId,status));
    }

    @PutMapping("/resetPwd/{ids}")
    public Result resetPwd(@PathVariable String[] ids){
        if (ArrayUtils.contains(ids, getUserId())) {
            return error("当前用户不能删除");
        }
        return toResult(userService.restPwd(ids));
    }

        @PutMapping("/user/updatePwd")
    public Result updataPwd(HttpServletRequest request ){
        Result result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_FAIL);
        int i = userService.updatePwd(request);
        if (i != 0) {
            result = ResultGenerator.returnCodeMessage(BussinessCode.RESULT_GLOBAL_SUCCESS);
        }
        return result;
    }




}
