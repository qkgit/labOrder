package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.domain.entity.SysRole;
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

}
