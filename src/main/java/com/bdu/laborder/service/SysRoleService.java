package com.bdu.laborder.service;

import com.bdu.laborder.common.core.domain.entity.SysRole;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/2/14 10:03
 */
public interface SysRoleService {

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRole> selectRoleList(SysRole role);
}
