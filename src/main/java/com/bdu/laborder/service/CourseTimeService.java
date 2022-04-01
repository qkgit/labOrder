package com.bdu.laborder.service;

import com.bdu.laborder.entity.CourseTime;

import java.util.List;

/**
 * @Title
 * @Author Qi
 * @data 2022/1/6 11:10
 */
public interface CourseTimeService {

    /**
     *  获取课程时间列表
     * @param courseTime
     * @return
     */
    public List<CourseTime> getTimeList(CourseTime courseTime);

    /**
     * 根据id获取课程时间详细信息
     * @param id
     * @return
     */
    public CourseTime getTime(String id);

    /**
     *  查询默认时间配置
     * @return
     */
    public CourseTime getDefaultTime();

    /**
     *  添加课程时间
     * @param courseTime
     * @return
     */
    public int insertTime(CourseTime courseTime);

    /**
     * 更新课程时间
     * @param courseTime
     * @return
     */
    public int updateTime(CourseTime courseTime);

    /**
     * 设为默认
     * @param id
     * @return
     */
    public int setDefaultTime(String id);

    /**
     * 生成新版本
     * @param id
     * @param creatUser
     * @return
     */
    public int creatNewVersion(String id,String creatUser);

    /**
     * 删除课程时间
     * @param ids
     * @return
     */
    public int deleteTimes(String[] ids);

}
