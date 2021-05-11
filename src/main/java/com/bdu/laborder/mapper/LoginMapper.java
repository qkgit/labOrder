package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.User;
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

    User getUserByUserName(@Param("loginName")String loginName);

    User getUserById(@Param("userId")String userId);



}
