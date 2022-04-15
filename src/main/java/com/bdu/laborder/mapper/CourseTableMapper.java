package com.bdu.laborder.mapper;

import com.bdu.laborder.entity.CourseTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qi
 * @data 2022/3/27 15:56
 */
@Mapper
@Repository
public interface CourseTableMapper {

    List<CourseTable> getCourseTableList(CourseTable courseTable);


    /**
     * 查询教师课表
     * @param teacherId 教师id
     * @return
     */
    List<CourseTable> getCourseTableListByTeacher(String teacherId);

    /**
     * 校验 该班级 同年同学期同周同节 是否已经存在配置课程
     * @param table
     * @return
     */
    CourseTable checkCourseTableUnique(CourseTable table);

    CourseTable selectCourseTableById(String id);

    int insertCourseTable(CourseTable table);

    int updateCourseTable(CourseTable table);

    int deleteCourseTableByIds(String[] ids);


}
