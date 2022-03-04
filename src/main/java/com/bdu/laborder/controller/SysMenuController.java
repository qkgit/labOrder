package com.bdu.laborder.controller;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.controller.BaseController;
import com.bdu.laborder.common.core.domain.entity.SysMenu;
import com.bdu.laborder.common.core.result.Result;
import com.bdu.laborder.service.SysMenuService;
import com.bdu.laborder.utils.PageQuery;
import com.bdu.laborder.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping
    public Result add(@Validated @RequestBody SysMenu menu){
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(getUserName());
        return toResult(menuService.insertMenu(menu));
    }
}
