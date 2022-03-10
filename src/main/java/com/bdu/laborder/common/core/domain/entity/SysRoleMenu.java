package com.bdu.laborder.common.core.domain.entity;

/**
 * @Title 角色和菜单关联 sys_role_menu
 * @Author Qi
 * @data 2022/3/10 9:14
 */
public class SysRoleMenu {

    /** 角色ID */
    private String roleId;
    /** 菜单ID*/
    private String menuId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
