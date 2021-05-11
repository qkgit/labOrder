package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.User;
import com.bdu.laborder.entity.UserRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qi
 * @data 2021/2/4 13:57
 */
@Repository
@Mapper
public interface UserMapper {

    /**
     * 查询用户列表
     */
    List<User> getUserList(UserRequest item);

    /**
     *  根据id查询用户信息
     */
    User getUserById(@Param("id") Integer id);

    /**
     *  新增用户
     */
    int addUser(User user);

    /**
     * 修改用户
     */
    int updateUser(User user);

    /**
     *  删除用户
     */
    int deleteUser(Integer id);

    /**
     * 根据登录名查找用户
     */
    Integer selectUserByLoginName( String loginName);

    /**
     *  修改密码
     */
    int updatePwd(Integer id,String pwd);

    /**
     * 修改用户头像
     * @param id
     * @param url
     * @return
     */
    int updateUserAvatar(String id,String url);

    int restPwd(@Param("id")int id,@Param("pwd")String pwd);
}
