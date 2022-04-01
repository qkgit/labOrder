package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/3/24 16:57
 */
@Mapper
@Repository
public interface CourseMapper {

    List<Course> selectCourseList(Course course);

    List<Course> getCourseListByNameOrLeader(String param);

    Course selectCourseById(String id);

    int insertCourse(Course course);

    int updateCourse(Course course);

    int removeCourseByIds(String[] ids);
}
