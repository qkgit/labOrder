package com.bdu.laborder.service.impl;

import com.bdu.laborder.entity.CourseTable;
import com.bdu.laborder.mapper.CourseTableMapper;
import com.bdu.laborder.service.CourseTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/4/1 14:26
 */
@Service
public class CourseTableServiceImpl implements CourseTableService {

    @Autowired
    private CourseTableMapper tableMapper;

    /**
     * 查询课程表列表
     *
     * @param table
     * @return
     */
    @Override
    public List<CourseTable> getCourseTableList(CourseTable table) {
        return tableMapper.getCourseTableList(table);
    }

    /**
     * 根据id查询课程表信息
     *
     * @param id
     * @return
     */
    @Override
    public CourseTable getCourseTableById(String id) {
        return null;
    }

    /**
     * 添加课程表
     *
     * @param table
     * @return
     */
    @Override
    public int addCourseTable(CourseTable table) {
        return 0;
    }
}
