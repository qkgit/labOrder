package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.BussinessCode;
import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.TreeSelect;
import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.domain.entity.SysDept;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.common.core.result.ResultGenerator;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.service.SysDeptService;
import com.bdu.laborder.service.SysUserService;
import com.bdu.laborder.utils.PageQuery;
import com.bdu.laborder.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.tree.TreePath;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户管理控制层
 *
 * @Author Qi
 * @data 2021/2/4 13:56
 */
@RestController
public class SysUserController extends BaseController {

    @Autowired
    SysUserService userService;
    @Autowired
    private SysDeptService deptService;

    @PostMapping("/users")
    public Result getUserList(@RequestBody PageQuery pageQuery) {
        PageInfo page = pageQuery.getPage();
        SysUser user = getParam(pageQuery, SysUser.class);
        List<SysUser> userList = userService.getUserList(user);
        List<SysUser> resultList = userList.stream()
                .skip((page.getPageNum()-1) * page.getPageSize())
                .limit(page.getPageSize())
                .collect(Collectors.toList());
        page.setList(resultList);
        page.setTotal(userList.size());
        return success(page);
    }

    @GetMapping("/user/{id}")
    public Result getUserById(@PathVariable String id) {
        if (StringUtils.isNotNull(id)) {
            SysUser user = userService.getUserById(id);
            if (user == null) {
                return error();
            }
            return success(user);
        } else {
            return ResultGenerator.returnCodeMessage(BussinessCode.RESULT_FIELD_NULL);
        }
    }

    @PostMapping("/user")
    public Result addUser(@RequestBody SysUser user) {
        String loginName = user.getLoginName();
        if (UserConstants.NOT_UNIQUE.equals(userService.checkLoginNameUnique(loginName))) {
            return error("新增用户'" + user.getRealName() + "'失败，学号或工号已存在！");
        }
        if (StringUtils.isNotEmpty(user.getMobile())
                && UserConstants.NOT_UNIQUE.equals(userService.checkMobileUnique(user))) {
            return error("新增用户'" + user.getRealName() + "'失败，手机号码已存在！");
        }
        if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return error("新增用户'" + user.getRealName() + "'失败，邮箱已存在！");
        }
        if (loginName.length() < 6) {
            return error("新增用户'" + user.getRealName() + "'失败，学号或工号格式不符合规范！");
        }
        String password = loginName.substring(loginName.length() - 6);
        user.setPassword(password);
        user.setCreateBy(getUserName());
        return toResult(userService.addUser(user));
    }

    @PutMapping("/user")
    public Result updateUser(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        if (StringUtils.isNotEmpty(user.getMobile())
                && UserConstants.NOT_UNIQUE.equals(userService.checkMobileUnique(user))) {
            return error("新增用户'" + user.getRealName() + "'失败，手机号码已存在！");
        }
        if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return error("新增用户'" + user.getRealName() + "'失败，邮箱已存在！");
        }
        user.setUpdateBy(getUserName());
        return toResult(userService.updateUser(user));
    }

    @DeleteMapping("/user/{ids}")
    public Result deleteUser(@PathVariable String[] ids) {
        if (ArrayUtils.contains(ids, getUserId())) {
            return error("当前用户不能删除");
        }
        return toResult(userService.deleteUser(ids));
    }

    @PutMapping("/user/changeStatus")
    public Result changeUserStatus(@RequestParam(value = "userId") String userId, @RequestParam(value = "status") String status) {
        return toResult(userService.updateUserStatus(userId, status));
    }

    @PutMapping("/resetPwd/{ids}")
    public Result resetPwd(@PathVariable String[] ids) {
        if (ArrayUtils.contains(ids, getUserId())) {
            return error("当前用户不能删除");
        }
        return toResult(userService.restPwd(ids));
    }

/**============================= 个人信息 ==========================================*/

    @PutMapping("/user/profile/updatePwd")
    public Result updatePwd(HttpServletRequest request) {
        return toResult(userService.updatePwd(request));
    }

    @PutMapping("/user/profile")
    public Result updateProfile (@RequestBody SysUser user){
        System.out.println(user);
        return toResult(1);
    }

    @PostMapping("/user/profile/avatar")
    public Result uploadAvatar(@RequestParam("/avatarfile")MultipartFile file) throws IOException {
        return error("上传图片异常，请联系管理员");
//        return success();
    }




/******************************** 用户树 ***************************************************/
    @GetMapping("/userTreeSelect/{roleId}")
    public Result getDeptUserTreeSelectByRoleId(@PathVariable String roleId) {
        SysUser sysUser = new SysUser();
        sysUser.setRoleId(roleId);
        List<SysUser> userList = userService.getUserList(sysUser);
        List<TreeSelect> deptUserTreeSelect = deptService.buildDeptUserTreeSelct(userList);
        return success(deptUserTreeSelect);
    }


}
