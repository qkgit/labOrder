package com.bdu.laborder.service.impl;

import com.bdu.laborder.entity.Classroom;
import com.bdu.laborder.entity.CourseTime;
import com.bdu.laborder.mapper.ClassroomMapper;
import com.bdu.laborder.service.ClassroomService;
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

    @Override
    public List<Classroom> getRoomList(Classroom classroom) {
        return classroomMapper.selectClassroomList(classroom);
    }
}
