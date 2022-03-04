package com.bdu.laborder.controller;

import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.domain.entity.SysRole;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.service.SysRoleService;
import com.bdu.laborder.utils.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
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
    SysRoleService sysRoleService;


    @PostMapping("/list")
    public Result list(@RequestBody PageQuery pageQuery){
        startPage(pageQuery);
        SysRole role = getParam(pageQuery, SysRole.class);
        List<SysRole> sysRoles = sysRoleService.selectRoleList(role);
        return getPageInfo(sysRoles);
    }

}
