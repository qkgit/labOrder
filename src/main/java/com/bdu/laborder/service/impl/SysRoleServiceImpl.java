package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.core.domain.entity.SysRole;
import com.bdu.laborder.mapper.SysRoleMapper;
import com.bdu.laborder.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/2/14 10:04
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> selectRoleList(SysRole role) {
        return sysRoleMapper.selectRoleList(role);
    }
}
