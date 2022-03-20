package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.domain.entity.SysRole;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.service.SysRoleService;
import com.bdu.laborder.utils.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/2/14 9:55
 */
@RestController
@RequestMapping("/role")
public class SysRoleController extends BaseController {
    @Autowired
    SysRoleService roleService;


    @PostMapping("/list")
    public Result list(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        SysRole role = getParam(pageQuery, SysRole.class);
        List<SysRole> sysRoles = roleService.selectRoleList(role);
        return getPageInfo(sysRoles);
    }

    @GetMapping("/{roleId}")
    public Result getInfo(@PathVariable String roleId){
        return success(roleService.selectRoleById(roleId));
    }

    @PostMapping
    public Result add(@Validated @RequestBody SysRole role){
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return error("新增角色'" + role.getRoleName() + "'失败，角色权限字符已存在");
        }
        role.setCreateBy(getUserName());
        return toResult(roleService.insertRole(role));
    }

    @PutMapping
    public Result edit(@Validated @RequestBody SysRole role){
        roleService.checkRoleAllowed(role);
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateBy(getUserName());
        return toResult(roleService.updateRole(role));
    }

    @DeleteMapping("/{roleIds}")
    public Result remove(@PathVariable String[] roleIds) {
        return toResult(roleService.deleteRoleByIds(roleIds));
    }

    @PutMapping("/changeStatus")
    public Result changeStatus(@RequestBody SysRole role){
        roleService.checkRoleAllowed(role);
        role.setUpdateBy(getUserName());
        return toResult(roleService.updateRoleStatus(role));
    }

    /**
     * 查询已分配用户角色列表
     * @param pageQuery
     * @return
     */
    @PostMapping("/authUser/allocatedList")
    public Result allocatedUserList(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        SysUser user = getParam(pageQuery, SysUser.class);
        List<SysUser> sysRoles = roleService.selectAllocatedList(user);
        return getPageInfo(sysRoles);
    }

    @PostMapping("/authUser/unallocatedList")
    public Result unallocatedUserList(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        SysUser user = getParam(pageQuery, SysUser.class);
        List<SysUser> sysRoles = roleService.selectUnallocatedList(user);
        return getPageInfo(sysRoles);
    }

    /**
     * (批量)取消授权用户
     * @param userIds
     * @param roleId
     * @return
     */
    @PutMapping("/authUser/cancel")
    public Result cancelAuthUser(String[] userIds,String roleId){
        return toResult(roleService.deleteAuthUsers(roleId,userIds));
    }

    /**
     * 批量选择用户授权
     * @param roleId
     * @param userIds
     * @return
     */
    @PutMapping("//authUser/selectAll")
    public Result selectAuthUserAll(String roleId,String[] userIds){
        return toResult(roleService.insertAuthUsers(roleId,userIds));
    }
}
