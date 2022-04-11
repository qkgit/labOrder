package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.entity.SysRole;
import com.bdu.laborder.common.core.domain.entity.SysRoleMenu;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.common.core.domain.entity.SysUserRole;
import com.bdu.laborder.exception.BaseException;
import com.bdu.laborder.mapper.SysRoleMapper;
import com.bdu.laborder.mapper.SysRoleMenuMapper;
import com.bdu.laborder.mapper.SysUserRoleMapper;
import com.bdu.laborder.service.SysRoleService;
import com.bdu.laborder.utils.StringUtils;
import com.bdu.laborder.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/2/14 10:04
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Override
    public List<SysRole> selectRoleList(SysRole role) {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(String roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(SysRole role) {
        role.setRoleId(UuidUtil.getUuid());
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(SysRole role) {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (String menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(SysRole role) {
        // 修改角色信息
        roleMapper.updateRole(role);
        // 重置角色菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRoleStatus(SysRole role) {
        return roleMapper.updateRole(role);
    }

    /**
     * 批量删除角色信息
     *
     * @param roleIds
     * @return
     */
    @Override
    @Transactional
    public int deleteRoleByIds(String[] roleIds) {
        for (String roleId : roleIds) {
            checkRoleAllowed(new SysRole(roleId));
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new BaseException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenu(roleIds);
        // 删除角色与部门关联
        // roleDeptMapper.deleteRoleDept(roleIds);
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(String roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(SysRole role) {
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && !StringUtils.equals(info.getRoleId(),role.getRoleId())){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(SysRole role) {
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && !StringUtils.equals(info.getRoleId(),role.getRoleId())){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    @Override
    public void checkRoleAllowed(SysRole role) {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin()) {
            throw new BaseException("不允许操作超级管理员用户");
        }
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectAllocatedList(SysUser user) {
        return userRoleMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return userRoleMapper.selectUnallocatedList(user);
    }

    /**
     * (批量)取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    @Override
    public int deleteAuthUsers(String roleId, String[] userIds) {
        return userRoleMapper.deleteUserRoleInfos(roleId,userIds);
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    @Override
    public int insertAuthUsers(String roleId, String[] userIds) {
        ArrayList<SysUserRole> list = new ArrayList<>();
        for (String userId : userIds) {
            SysUserRole ur = new SysUserRole();
            ur.setRoleId(roleId);
            ur.setUserId(userId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }
}
