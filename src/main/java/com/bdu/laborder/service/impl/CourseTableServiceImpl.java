package com.bdu.laborder.service.impl;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.entity.CourseTable;
import com.bdu.laborder.mapper.CourseTableMapper;
import com.bdu.laborder.service.CourseTableService;
import com.bdu.laborder.utils.StringUtils;
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
        return tableMapper.selectCourseTableById(id);
    }

    /**
     * 添加课程表
     *
     * @param table
     * @return
     */
    @Override
    public int addCourseTable(CourseTable table) {
        return tableMapper.insertCourseTable(table);
    }

    /**
     * 修改课程表
     *
     * @param table
     * @return
     */
    @Override
    public int updateCourseTable(CourseTable table) {
        return tableMapper.updateCourseTable(table);
    }

    /**
     * 删除课程表
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteCourseTableByIds(String[] ids) {
        return tableMapper.deleteCourseTableByIds(ids);
    }

    /**
     * 校验 该班级 同年同学期同周同节 是否已经存在配置课程
     *
     * @param table
     * @return
     */
    @Override
    public String checkCourseTableUnique(CourseTable table) {
        CourseTable info = tableMapper.checkCourseTableUnique(table);
        if (StringUtils.isNotNull(info) && !info.getUuid().equals(table.getUuid())){
            return UserConstants.NOT_UNIQUE;
        }else {
            return UserConstants.UNIQUE;
        }
    }
}
