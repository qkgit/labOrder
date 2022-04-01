package com.bdu.laborder.service;

import com.bdu.laborder.entity.Course;
import com.bdu.laborder.entity.CourseTable;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/4/1 14:19
 */
public interface CourseTableService {

    /**
     * 查询课程表列表
     * @param table
     * @return
     */
    public List<CourseTable> getCourseTableList(CourseTable table);

    /**
     * 根据id查询课程表信息
     * @param id
     * @return
     */
    public CourseTable getCourseTableById(String id);

    /**
     * 添加课程表
     * @param table
     * @return
     */
    public int addCourseTable(CourseTable table);

}
