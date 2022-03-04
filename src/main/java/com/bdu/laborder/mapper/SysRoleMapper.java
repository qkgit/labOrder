package com.bdu.laborder.mapper;

import com.bdu.laborder.common.core.domain.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/2/14 10:04
 */
@Repository
@Mapper
public interface SysRoleMapper {
    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRole> selectRoleList(SysRole role);

    /**
     * 根据id 查询用户角色
     * @param id
     * @return
     */
    List<SysRole> getUserRolesById(String id);
}
