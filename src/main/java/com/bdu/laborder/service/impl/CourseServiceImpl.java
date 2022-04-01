package com.bdu.laborder.service.impl;

import com.bdu.laborder.entity.Course;
import com.bdu.laborder.mapper.CourseMapper;
import com.bdu.laborder.mapper.SysUserMapper;
import com.bdu.laborder.service.CourseService;
import com.bdu.laborder.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/3/24 16:57
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private SysUserMapper userMapper;


    @Override
    public List<Course> getCourseList(Course course) {
        return courseMapper.selectCourseList(course);
    }

    @Override
    public List<Course> getCourseListByNameOrLeader(String param) {
        return courseMapper.getCourseListByNameOrLeader(param);
    }

    @Override
    public Course getCourseById(String id) {
        return courseMapper.selectCourseById(id);
    }

    @Override
    public int addCourse(Course course) {
        course.setUuid(UuidUtil.getUuid());
        course.setLeader(userMapper.getUserById(course.getLeaderId()).getRealName());
        return courseMapper.insertCourse(course);
    }

    @Override
    public int updateCourse(Course course) {
        course.setLeader(userMapper.getUserById(course.getLeaderId()).getRealName());
        return courseMapper.updateCourse(course);
    }

    @Override
    public int removeCourseByIds(String[] ids) {
        return courseMapper.removeCourseByIds(ids);
    }
}
