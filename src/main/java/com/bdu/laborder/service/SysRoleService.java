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

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    public SysRole selectRoleById(String roleId);

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public int insertRole(SysRole role);

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public int updateRole(SysRole role);

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    public int updateRoleStatus(SysRole role);

    /**
     * 批量删除角色信息
     * @param roleIds
     * @return
     */
    public int deleteRoleByIds(String[] roleIds);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int countUserRoleByRoleId(String roleId);

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    public String checkRoleNameUnique(SysRole role);

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    public String checkRoleKeyUnique(SysRole role);

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    public void checkRoleAllowed(SysRole role);

}