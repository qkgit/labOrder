package com.bdu.laborder.mapper;

import com.bdu.laborder.common.core.domain.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author Qi
 * @data 2020/12/13 18:50
 */
@Repository
@Mapper
public interface LoginMapper {

    SysUser getUserByUserName(@Param("loginName")String loginName);

    SysUser getUserById(@Param("userId")String userId);



}
