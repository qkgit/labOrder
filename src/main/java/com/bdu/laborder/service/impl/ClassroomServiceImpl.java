package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.entity.Classroom;
import com.bdu.laborder.entity.CourseTime;
import com.bdu.laborder.mapper.ClassroomMapper;
import com.bdu.laborder.mapper.SysUserMapper;
import com.bdu.laborder.service.ClassroomService;
import com.bdu.laborder.utils.StringUtils;
import com.bdu.laborder.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/1/24 17:31
 */
@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    ClassroomMapper classroomMapper;
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public List<Classroom> getRoomList(Classroom classroom) {
        return classroomMapper.selectClassroomList(classroom);
    }

    /**
     * 根据教室id获取教室信息
     *
     * @param id
     * @return
     */
    @Override
    public Classroom getRoomById(String id) {
        return classroomMapper.getClassroom(id);
    }

    /**
     * 新增教室
     *
     * @param classroom
     * @return
     */
    @Override
    public int addClassroom(Classroom classroom) {
        classroom.setUuid(UuidUtil.getUuid());
        classroom.setLeader(userMapper.getUserById(classroom.getLeaderId()).getRealName());
        return classroomMapper.insertClassroom(classroom);
    }

    /**
     * 更新教室信息
     *
     * @param classroom
     * @return
     */
    @Override
    public int updateClassroom(Classroom classroom) {
        return classroomMapper.updateClassroom(classroom);
    }

    /**
     * 批量删除教室
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteClassroomByIds(String[] ids) {
        return classroomMapper.deleteClassroomByIds(ids);
    }

    /**
     * 校验教室地址
     *
     * @param classroom
     * @return
     */
    @Override
    public String checkAddressUnique(Classroom classroom) {
        Classroom info = classroomMapper.checkAddressUnique(classroom);
        if (StringUtils.isNotNull(info) && !info.getUuid().equals(classroom.getUuid())) {
            return UserConstants.NOT_UNIQUE;
        }else {
            return UserConstants.UNIQUE;
        }
    }
}
