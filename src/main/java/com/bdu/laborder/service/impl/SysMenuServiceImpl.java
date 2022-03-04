package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.entity.SysMenu;
import com.bdu.laborder.common.core.domain.service.UserService;
import com.bdu.laborder.mapper.SysMenuMapper;
import com.bdu.laborder.mapper.SysRoleMapper;
import com.bdu.laborder.service.SysMenuService;
import com.bdu.laborder.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/2/18 16:49
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    SysMenuMapper menuMapper;
    @Autowired
    SysRoleMapper roleMapper;

    @Override
    public List<SysMenu> selectMenuList(String userId) {
        return selectMenuList(new SysMenu(), userId);
    }

    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, String userId) {
        List<SysMenu> menuList = null;
        // 管理员显示所有菜单信息
        if (UserService.isAdmin(roleMapper.getUserRolesById(userId))) {
            menuList = menuMapper.selectMenuList(menu);
        } else {
            menu.getParams().put("userId", userId);
            menuList = menuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }

    @Override
    public int insertMenu(SysMenu menu) {
        return 0;
    }

    @Override
    public String checkMenuNameUnique(SysMenu menu) {
        if(StringUtils.isNotEmpty(menu.getMenuId())){
            SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
            if (StringUtils.isNotNull(info)&&info.getMenuId().equals(menu.getMenuId())){
                return UserConstants.UNIQUE;
            }
        }
        return UserConstants.NOT_UNIQUE;
    }
}
