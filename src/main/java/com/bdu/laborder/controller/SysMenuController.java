package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.domain.entity.SysMenu;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.service.SysMenuService;
import com.bdu.laborder.utils.StringUtils;
import com.bdu.laborder.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/2/17 10:38
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController extends BaseController {

    @Autowired
    SysMenuService menuService;

    @GetMapping("/list")
    public Result list(SysMenu menu){
        List<SysMenu> menuList = menuService.selectMenuList(menu, getUserId());
        return success(menuList);
    }

    @GetMapping("/{menuId}")
    public Result getInfo(@PathVariable String menuId){
        return success(menuService.selectMenuById(menuId));
    }

    @PostMapping
    public Result add(@Validated @RequestBody SysMenu menu){
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(getUserName());
        menu.setMenuId(UuidUtil.getUuid());
        return toResult(menuService.insertMenu(menu));
    }

    @PutMapping
    public Result edit(@Validated @RequestBody SysMenu menu){
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return error("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return error("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menu.setUpdateBy(getUserName());
        return success(menuService.updateMenu(menu));
    }

    @DeleteMapping("/{menuId}")
    public Result remove(@PathVariable("menuId") String menuId){
        if (menuService.hasChildByMenuId(menuId)) {
            return error("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return error("菜单已分配,不允许删除");
        }
        return toResult(menuService.deleteMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public Result treeSelect(SysMenu menu){
        List<SysMenu> menuList = menuService.selectMenuList(menu, getUserId());
        return success(menuService.buildMenuTreeSelect(menuList));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public Result roleMenuTreeSelect(@PathVariable("roleId") String roleId){
        List<SysMenu> menus = menuService.selectMenuList(getUserId());
        HashMap<String, List> resultMap = new HashMap<>();
        resultMap.put("menus",menuService.buildMenuTreeSelect(menus));
        resultMap.put("checkedKeys",null);
        return success(resultMap);
    }
}
