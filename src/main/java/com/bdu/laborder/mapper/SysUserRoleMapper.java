package com.bdu.laborder.mapper;

import com.bdu.laborder.common.core.domain.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2021/12/3 10:50
 */
@Repository
@Mapper
public interface SysUserRoleMapper {
    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    public int batchUserRole(List<SysUserRole> userRoleList);
}
