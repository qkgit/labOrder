package com.bdu.laborder.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Title 角色与菜单关联表 数据层
 * @Author Qi
 * @data 2022/3/10 9:06
 */
@Repository
@Mapper
public interface SysRoleMenuMapper {

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int checkMenuExistRole(String menuId);
}
