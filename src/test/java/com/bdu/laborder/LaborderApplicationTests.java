package com.bdu.laborder;

import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.mapper.SysDeptMapper;
import com.bdu.laborder.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class LaborderApplicationTests {

    @Autowired
    SysDeptMapper deptMapper;
    @Autowired
    SysUserMapper userMapper;

    @Test
    void contextLoads() {
        List<SysUser> userList = userMapper.getUserList(new SysUser());
        if (userList.get(1).getDept() != null){
            System.out.println(userList.get(1).getDept().toString());
        }
        System.out.println("null");
    }



}
